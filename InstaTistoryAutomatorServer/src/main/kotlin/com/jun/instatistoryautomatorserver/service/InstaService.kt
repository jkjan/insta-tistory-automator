package com.jun.instatistoryautomatorserver.service

import com.jun.instatistoryautomatorserver.dto.InstaApi
import com.jun.instatistoryautomatorserver.dto.InstaPostDTO
import com.jun.instatistoryautomatorserver.dto.InstaResponseDTO
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.exception.InstaException
import com.jun.instatistoryautomatorserver.property.InstaProperty
import com.jun.instatistoryautomatorserver.repository.InstaRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
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
    suspend fun fetchInstaPost() {
        val lastInstaTimestamp = getLastInstaTimestamp()

        val initialUrl =
            with(instaProperty) {
                val since = lastInstaTimestamp.toInstant().epochSecond.toString()
                val mediaFieldsStr = mediaFields.joinToString(",")
                "$baseUrl/$apiVersion/$userId/media?access_token=$userToken&fields=$mediaFieldsStr&since=$since"
            }

        getInstaPosts(initialUrl)
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

    suspend fun getInstaPosts(url: String) {
        coroutineScope {
            launch {
                val instaResponseDTO = instaApi.getInstaPosts(url)
                logger.info { "GET request: $url" }

                if (instaResponseDTO.isSuccessful) {
                    with(instaResponseDTO) {
                        if (code() == HttpStatus.OK.value() && body() != null) {
                            logger.info { code() }
                            logger.info { body() }
                            with(body()!!) {
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
                                }.let {
                                    instaRepository.saveAll(it)
                                }

                                if (paging.next != null) {
                                    getInstaPosts(paging.next!!)
                                }
                            }
                        } else {
                            throw InstaException("인스타 API 호출 오류. code: ${code()}, body: ${body()}")
                        }
                    }
                }
            }
        }
    }

    fun String.correct(): String {
        return StringBuilder(this).apply { insert(22, ':') }.toString()
    }

    //    fun getInstaPostsSince(offsetDateTime: OffsetDateTime): InstaResponseDTO {
//        val since = offsetDateTime.toInstant().toString()
//
//        return with(instaProperty) {
//                val mediaFieldsStr = mediaFields.joinToString(",")
//
//                instaApi.getInstaPosts(
//                    apiVersion = apiVersion,
//                    userId = userId,
//                    options = mapOf(
//                        "access_token" to userToken,
//                        "fields" to mediaFieldsStr,
//                        "since" to since,
//                    )
//                )
//            }
//    }
    companion object {
        const val FIRST_BEER_POST_TIMESTAMP = "2024-06-20T12:00:53+0000"
        val logger = KotlinLogging.logger {}
    }
}