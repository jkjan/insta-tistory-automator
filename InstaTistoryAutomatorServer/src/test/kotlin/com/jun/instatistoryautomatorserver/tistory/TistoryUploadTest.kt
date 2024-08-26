package com.jun.instatistoryautomatorserver.tistory

import com.jun.instatistoryautomatorserver.application.model.TistoryService
import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["webdriver.chrome-driver-path=src/main/resources/chromedriver"])
@ActiveProfiles("test", "credentials")
class TistoryServiceTest {
    @Autowired
    lateinit var tistoryService: TistoryService

    @Test
    @Disabled
    fun `유효한 글이 티스토리에 올라가야 함`() {
        // given
        val tistoryRequestDTO = TistoryRequestDTO(
            title = "test title",
            category = "술",
            imageUrl = "",
            content = "<p>test</p>",
            tags = listOf("tag1", "tag2", "tag3"),
        )

        tistoryService.uploadTistoryPost(tistoryRequestDTO)
    }
}