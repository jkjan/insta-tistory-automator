package com.jun.instatistoryautomatorserver.application.model

import com.jun.instatistoryautomatorserver.InstaTistoryAutomatorServerApplication.Companion.logger
import com.jun.instatistoryautomatorserver.adapter.out.api.InstaApi
import com.jun.instatistoryautomatorserver.adapter.out.db.InstaRepository
import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.domain.InstaPost
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.global.exception.InstaApiFetchException
import com.jun.instatistoryautomatorserver.global.property.InstaProperty
import com.jun.instatistoryautomatorserver.global.type.MediaType
import jakarta.transaction.Transactional
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
    suspend fun fetchInstaPosts(): List<InstaPostResponseDTO> {
        val initialUrl = getInitialUrl()

        val fetchedInstaPostResponse = getNewInstaPosts(initialUrl)
        val instaPosts = fetchedInstaPostResponse.map { it.toNewInstaPost() }

        saveInstaPosts(instaPosts)

        return fetchedInstaPostResponse
    }

    fun getFetchedInstaPosts() =
        instaRepository.findByTistoryFetched(tistoryFetched = false)

    fun saveInstaPosts(instaPosts: List<InstaPost>) {
        instaRepository.saveAll(instaPosts)
    }

    private fun getInitialUrl(): String {
        val lastInstaTimestamp = getLastInstaTimestamp()

        with(instaProperty) {
            val since = lastInstaTimestamp.toInstant().epochSecond.toString()
            val mediaFieldsStr = mediaFields.joinToString(",")
            return "$baseUrl/$apiVersion/$userId/media?access_token=$userToken&fields=$mediaFieldsStr&since=$since"
        }
    }

    @ThrowWithMessage("Instagram API 호출에 실패했습니다.", InstaApiFetchException::class)
    private fun getLastInstaTimestamp(): OffsetDateTime {
        val lastInstaPost = instaRepository.findFirstByOrderByTimestampDesc()

        return lastInstaPost?.timestamp
            ?: OffsetDateTime.parse(FIRST_BEER_POST_TIMESTAMP.correct(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    private suspend fun getNewInstaPosts(url: String): List<InstaPostResponseDTO> {
        logger.info { "인스타 API 요청: $url" }
        val instaResponseDTO = instaApi.getInstaPosts(url)

        if (instaResponseDTO.isOkAndHasBody()) {
            with(instaResponseDTO.body()!!) {
                val nextInstaPosts = paging?.next?.let {
                    getNewInstaPosts(it)
                } ?: listOf()

                val instaPostFromResponse = `data`.filter { it.mediaType != MediaType.VIDEO }.reversed()

                return nextInstaPosts + instaPostFromResponse
            }
        } else {
            throw InstaApiFetchException("인스타 API 호출했으나 성공적이지 않음")
        }
    }

    companion object {
        const val FIRST_BEER_POST_TIMESTAMP = "2024-08-25T12:00:53+0000"

        fun <T> Response<T>.isOkAndHasBody(): Boolean = this.isSuccessful && this.body() != null

        fun String.correct(): String = StringBuilder(this).apply { insert(22, ':') }.toString()
    }
}
