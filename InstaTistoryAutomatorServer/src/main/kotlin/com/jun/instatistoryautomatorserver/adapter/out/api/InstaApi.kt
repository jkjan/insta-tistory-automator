package com.jun.instatistoryautomatorserver.adapter.out.api

import com.jun.instatistoryautomatorserver.InstaTistoryAutomatorServerApplication.Companion.logger
import com.jun.instatistoryautomatorserver.application.dto.InstaApiResponseDTO
import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.global.exception.InstaApiFetchException
import com.jun.instatistoryautomatorserver.global.type.MediaType
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

                val instaPostFromResponse = `data`.filter { it.mediaType != MediaType.VIDEO }.reversed()

                return nextInstaPosts + instaPostFromResponse
            }
        } else {
            return emptyList()
        }
    }
}
