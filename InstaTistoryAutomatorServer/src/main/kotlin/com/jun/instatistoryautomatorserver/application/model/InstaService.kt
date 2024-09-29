package com.jun.instatistoryautomatorserver.application.model

import com.jun.instatistoryautomatorserver.adapter.out.api.InstaApi
import com.jun.instatistoryautomatorserver.adapter.out.db.InstaRepository
import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.domain.InstaPost
import com.jun.instatistoryautomatorserver.global.property.InstaProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
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
    fun fetchInstaPosts(): List<InstaPostResponseDTO> {
        val initialUrl = getInitialUrl()

        val fetchedInstaPostResponse = instaApi.getNewInstaPosts(initialUrl)

        val instaPosts = fetchedInstaPostResponse.map { it.toNewInstaPost() }

        saveInstaPosts(instaPosts)

        logger.info { "${instaPosts.size} 개의 게시글을 찾았습니다." }

        return fetchedInstaPostResponse
    }

    fun getFetchedInstaPosts() =
        instaRepository.findByTistoryFetched(tistoryFetched = false)

    fun saveInstaPosts(instaPosts: List<InstaPost>) {
        instaRepository.saveAll(instaPosts)
    }

    fun getInitialUrl(): String {
        val lastInstaTimestamp = getLastInstaTimestamp()

        with(instaProperty) {
            val since = lastInstaTimestamp.toInstant().epochSecond.toString()
            val mediaFieldsStr = mediaFields.joinToString(",")
            return "$baseUrl/$apiVersion/$userId/media?access_token=$userToken&fields=$mediaFieldsStr&since=$since"
        }
    }

    private fun getLastInstaTimestamp(): OffsetDateTime {
        val lastInstaPost = instaRepository.findFirstByOrderByTimestampDesc()

        return lastInstaPost?.timestamp
            ?: OffsetDateTime.parse(FIRST_BEER_POST_TIMESTAMP.correct(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    companion object {
        const val FIRST_BEER_POST_TIMESTAMP = "2024-08-25T12:00:53+0000"

        fun String.correct(): String = StringBuilder(this).apply { insert(22, ':') }.toString()

        val logger = KotlinLogging.logger { }
    }
}
