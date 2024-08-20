package com.jun.instatistoryautomatorserver.service

import com.jun.instatistoryautomatorserver.config.RetrofitConfig
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.property.InstaProperty
import com.jun.instatistoryautomatorserver.repository.InstaRepository
import com.jun.instatistoryautomatorserver.service.InstaServiceTest.Companion.PORT
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@WebMvcTest(InstaService::class, InstaProperty::class, RetrofitConfig::class)
@AutoConfigureWireMock(port = PORT)
class InstaServiceTest {
    @MockBean
    lateinit var instaRepository: InstaRepository

    @SpyBean
    lateinit var instaService: InstaService

    @Test
    fun fetchInstaPost() = runTest {
        val testUrl = "http://localhost:$PORT/valid-insta-api-entry-with-next-page"
        given(instaService.getInitialUrl()).willReturn(testUrl)
        val instaPosts = instaService.fetchInstaPost()

        assertThat(instaPosts).hasSize(6)
        assertThat(instaPosts[0].instaId).isEqualTo("1")
        assertThat(instaPosts[1].instaId).isEqualTo("2")
        assertThat(instaPosts[2].instaId).isEqualTo("3")
        assertThat(instaPosts[3].instaId).isEqualTo("4")
        assertThat(instaPosts[4].instaId).isEqualTo("5")
        assertThat(instaPosts[5].instaId).isEqualTo("6")
    }

    companion object {
        const val PORT = 6000
    }
}