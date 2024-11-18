package com.jun.instatistoryautomatorserver.tistory

import com.jun.instatistoryautomatorserver.application.model.TistoryService
import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["webdriver.chrome-driver-path=src/main/resources/chromedriver"])
@Disabled
class TistoryUploadTest {
    @Autowired
    lateinit var tistoryService: TistoryService

    @Test
    fun `유효한 글이 티스토리에 올라가야 함`() {
        // given
        val tistoryRequestDTO = TistoryRequestDTO(
            title = "test title",
            category = "술",
            content = "<p>test</p>",
            tags = listOf("tag1", "tag2", "tag3"),
        )

        val testUrl = tistoryService.uploadTistoryPost(tistoryRequestDTO)
        logger.info { testUrl }
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
