package com.jun.instatistoryautomatorserver.insta

import com.jun.instatistoryautomatorserver.application.model.InstaService
import com.jun.instatistoryautomatorserver.global.exception.InstaException
import com.jun.instatistoryautomatorserver.insta.InstaServiceTest.Companion.PORT
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.given
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@AutoConfigureWireMock(port = PORT)
@Import(InstaTestConfig::class)
class InstaServiceTest {
    @SpyBean
    lateinit var instaService: InstaService

    @Test
    fun `유효한 인스타 API로 데이터 얻기 성공`() =
        runTest {
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

    @Test
    fun `유효하지 않은 인스타 API로 데이터 얻기 실패, 예외 전파`() =
        runTest {
            val testUrl = "http://localhost:$PORT/valid-insta-api-entry-with-invalid-next-page"
            given(instaService.getInitialUrl()).willReturn(testUrl)

            assertThrows<InstaException> {
                instaService.fetchInstaPost()
            }
        }

    companion object {
        const val PORT = 6000
    }
}
