package com.jun.instatistoryautomatorserver.application.model

import com.jun.instatistoryautomatorserver.adapter.out.api.InstaApi
import com.jun.instatistoryautomatorserver.adapter.out.db.InstaRepository
import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.global.exception.InstaException
import com.jun.instatistoryautomatorserver.global.property.InstaProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
    @ThrowWithMessage("Instagram API 호출에 실패했습니다.", InstaException::class)
    suspend fun fetchInstaPost(): List<InstaPostResponseDTO> {
        val initialUrl = getInitialUrl()

        val fetchedInstaPostResponse =
            coroutineScope {
                async {
                    getInstaPosts(initialUrl)
                }.await()
            }

        val instaPosts = fetchedInstaPostResponse.map { it.toNewInstaPost() }

        instaRepository.saveAll(instaPosts)

        return fetchedInstaPostResponse
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
        } else {
            OffsetDateTime.parse(FIRST_BEER_POST_TIMESTAMP.correct(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    suspend fun getInstaPosts(url: String): List<InstaPostResponseDTO> {
        logger.info { "인스타 API 요청: $url" }
        val instaResponseDTO = instaApi.getInstaPosts(url)

        if (instaResponseDTO.isOkAndHasBody()) {
            with(instaResponseDTO.body()!!) {
                val nextInstaPosts = paging?.next?.let {
                    getInstaPosts(it)
                } ?: listOf()

                val instaPostFromResponse = `data`.reversed()

                return nextInstaPosts + instaPostFromResponse
            }
        } else {
            throw InstaException("인스타 API 호출했으나 성공적이지 않음")
        }
    }

    companion object {
        const val FIRST_BEER_POST_TIMESTAMP = "2024-08-25T12:00:53+0000"
        val logger = KotlinLogging.logger {}

        fun <T> Response<T>.isOkAndHasBody(): Boolean = this.isSuccessful && this.body() != null

        fun String.correct(): String = StringBuilder(this).apply { insert(22, ':') }.toString()
    }
}
