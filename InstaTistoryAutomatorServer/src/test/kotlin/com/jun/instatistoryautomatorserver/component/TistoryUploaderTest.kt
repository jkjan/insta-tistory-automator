package com.jun.instatistoryautomatorserver.component

import com.jun.instatistoryautomatorserver.dto.TistoryRequestDTO
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TistoryUploaderTest {
    @Autowired
    lateinit var tistoryUploader: TistoryUploader

    @Test
    fun uploadPostToTistoryBlog() {
        // given
        val tistoryRequestDTO = TistoryRequestDTO(
            title = "test title",
            category = "없는 것",
            imageUrl = "",
            content = "<p>test</p>",
            tags = listOf("tag1", "tag2", "tag3")
        )

        tistoryUploader.uploadPostToTistoryBlog(tistoryRequestDTO)
    }
}