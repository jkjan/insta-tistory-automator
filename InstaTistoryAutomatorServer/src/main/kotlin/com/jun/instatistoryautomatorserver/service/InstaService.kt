package com.jun.instatistoryautomatorserver.service

import com.jun.instatistoryautomatorserver.dto.InstaApi
import com.jun.instatistoryautomatorserver.dto.InstaResponseDTO
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.exception.InstaException
import com.jun.instatistoryautomatorserver.property.InstaProperty
import com.jun.instatistoryautomatorserver.repository.InstaRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import kotlinx.coroutines.*
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import retrofit2.Response
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
@EnableConfigurationProperties(InstaProperty::class)
class InstaService(
    private val instaRepository: InstaRepository,
    private val instaProperty: InstaProperty,
    private val instaApi: InstaApi,
) {
    @Transactional
    suspend fun fetchInstaPost() {
        val initialUrl = getInitialUrl()

        coroutineScope {
            launch {
                try {
                    instaRepository.saveAll(
                        getInstaPosts(initialUrl)
                    )
                } catch (e: Exception) {
                    throw InstaException("인스타 데이터 가져오기 실패", e)
                }
            }
        }
    }

    fun getInitialUrl(): String {
        val lastInstaTimestamp = getLastInstaTimestamp()

        with(instaProperty) {
            val since = lastInstaTimestamp.toInstant().epochSecond.toString()
            val mediaFieldsStr = mediaFields.joinToString(",")
            return "$baseUrl/$apiVersion/$userId/media?access_token=$userToken&fields=$mediaFieldsStr&since=$since"
        }
    }

    fun getLastInstaTimestamp(): OffsetDateTime {
        val lastInstaPost = instaRepository.findFirstByOrderByTimestampDesc()

        return if (lastInstaPost != null) {
            lastInstaPost.timestamp!!
        }
        else {
            OffsetDateTime.parse(FIRST_BEER_POST_TIMESTAMP.correct(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    suspend fun getInstaPosts(url: String): List<InstaPost> {
        val instaPosts = mutableListOf<InstaPost>()
        val instaResponseDTO = instaApi.getInstaPosts(url)
        logger.info { "인스타 API 요청: $url" }

        with(instaResponseDTO) {
            if (isOkAndHasBody()) {
                with(body()!!) {
                    paging?.next?.let {
                        coroutineScope {
                            launch {
                                getInstaPosts(it)
                            }.join()
                        }
                    }

                    return instaPosts +
                            `data`.map {
                                InstaPost(
                                    instaId = it.id,
                                    mediaUrl = it.mediaUrl,
                                    permalink = it.permalink,
                                    caption = it.caption,
                                    timestamp = OffsetDateTime.parse(it.timestamp!!.correct()),
                                    mediaType = it.mediaType,
                                    fetchedTimestamp = OffsetDateTime.now()
                                )
                            }.reversed()
                }
            } else {
                throw InstaException("인스타 API 호출했으나 성공적이지 않음")
            }
        }
    }

    fun String.correct(): String {
        return StringBuilder(this).apply { insert(22, ':') }.toString()
    }

    companion object {
        const val FIRST_BEER_POST_TIMESTAMP = "2024-06-20T12:00:53+0000"
        val logger = KotlinLogging.logger {}

        fun <T> Response<T>.isOkAndHasBody(): Boolean {
            return this.isSuccessful && this.body() != null
        }
    }
}