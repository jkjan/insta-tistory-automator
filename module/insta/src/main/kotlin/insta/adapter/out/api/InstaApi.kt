package insta.adapter.out.api

import common.adapter.out.api.BaseApi
import common.global.annotation.ThrowWithMessage
import insta.global.exception.InstaApiFetchException
import insta.application.dto.InstaApiResponseDTO
import insta.application.dto.InstaPostResponseDTO
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class InstaApi : BaseApi() {
    @ThrowWithMessage("인스타 API 호출했으나 성공적이지 않음.", InstaApiFetchException::class)
    fun getNewInstaPosts(url: String): List<InstaPostResponseDTO> {
        logger.info { "인스타 API 요청: $url" }
        val instaResponseDTO = restTemplate.getForEntity(url, InstaApiResponseDTO::class.java)

        if (instaResponseDTO.isOkAndHasBody()) {
            with(instaResponseDTO.body!!) {
                val nextInstaPosts = paging?.next?.let {
                    getNewInstaPosts(it)
                } ?: listOf()

                val instaPostFromResponse = `data`.filter { it.mediaType != insta.global.type.MediaType.VIDEO }.reversed()

                return nextInstaPosts + instaPostFromResponse
            }
        } else {
            return emptyList()
        }
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
