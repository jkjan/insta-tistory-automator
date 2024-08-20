package com.jun.instatistoryautomatorserver.tistory

import com.jun.instatistoryautomatorserver.component.TistoryUploader
import com.jun.instatistoryautomatorserver.config.WebDriverConfig
import com.jun.instatistoryautomatorserver.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.property.TistoryProperty
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@TestPropertySource(properties = ["webdriver.chrome-driver-path=src/main/resources/chromedriver"])
@ActiveProfiles("test", "credentials")
class TistoryUploaderTest {
    @Autowired
    lateinit var tistoryUploader: TistoryUploader

    @Test
    fun uploadPostToTistoryBlog() {
        // given
        val tistoryRequestDTO = TistoryRequestDTO(
            title = "test title",
            category = "술",
            imageUrl = "",
            content = "<p>test</p>",
            tags = listOf("tag1", "tag2", "tag3")
        )

        tistoryUploader.uploadPostToTistoryBlog(tistoryRequestDTO)
    }
}