package com.jun.instatistoryautomatorserver.insta

import com.jun.instatistoryautomatorserver.controller.InstaController
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.service.InstaService
import com.jun.instatistoryautomatorserver.insta.InstaServiceTest.Companion.PORT
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@WebMvcTest(InstaController::class)
@Import(InstaTestConfig::class)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = PORT)
open class InstaControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @SpyBean
    private lateinit var instaService: InstaService

    @Test
    open fun `인스타 게시글이 DB에 저장됨`() = runTest {
        // given
        val testUrl = "http://localhost:$PORT/valid-insta-api-entry-with-next-page"
        given(instaService.getInitialUrl()).willReturn(testUrl)

        val webMvcResult = webTestClient.get().uri("/api/v1/insta").exchange().expectBodyList(InstaPost::class.java).returnResult()

        with(webMvcResult) {
            assertThat(status).isEqualTo(HttpStatus.OK)
            assertThat(responseBody).hasSize(6)
        }
    }
}