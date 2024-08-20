package com.jun.instatistoryautomatorserver

import com.google.gson.Gson
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.repository.InstaRepository
import com.jun.instatistoryautomatorserver.service.InstaService
import com.jun.instatistoryautomatorserver.service.InstaServiceTest.Companion.PORT
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = PORT)
class InstaControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @SpyBean
    private lateinit var instaService: InstaService

    @Autowired
    private lateinit var instaRepository: InstaRepository

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `인스타 게시글이 DB에 저장됨`() = runTest {
        // given
        val testUrl = "http://localhost:$PORT/valid-insta-api-entry-with-next-page"
        given(instaService.getInitialUrl()).willReturn(testUrl)

        val webMvcResult = webTestClient.get().uri("/api/v1/insta").exchange().expectBodyList(InstaPost::class.java).returnResult()
        val dbInstaPosts = instaRepository.findAll()

        with(webMvcResult) {
            assertThat(status).isEqualTo(HttpStatus.OK)
            assertThat(responseBody).hasSize(6)
        }

        assertThat(dbInstaPosts).hasSize(6)
    }
}