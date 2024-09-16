package com.jun.instatistoryautomatorserver.insta

import com.google.gson.Gson
import com.jun.instatistoryautomatorserver.adapter.`in`.InstaController
import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.application.model.InstaService
import com.jun.instatistoryautomatorserver.global.type.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(InstaController::class)
open class InstaControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var instaService: InstaService

    @Test
    open fun `인스타 게시글 가져오는 API 성공`() {
        // given
        given(instaService.fetchInstaPosts()).willReturn(
            listOf(
                InstaPostResponseDTO(
                    id = "test",
                    mediaType = MediaType.IMAGE,
                    mediaUrl = "https://test.com/",
                    caption = "내용",
                    permalink = "https://test.insta.com/",
                    timestamp = "2024-09-08T17:27:04",
                ),
            ),
        )

        // when
        val response =
            mockMvc.perform(
                get("/api/v1/insta"),
            ).andReturn()
                .response

        val body = Gson().fromJson(response.contentAsString, Array<InstaPostResponseDTO>::class.java)

        // then
        with(response) {
            assertThat(status).isEqualTo(HttpStatus.OK.value())
            assertThat(body).hasSize(1)
        }
    }
}
