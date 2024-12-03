package tistory

import insta.adapter.out.db.InstaRepository
import insta.application.model.InstaService
import insta.domain.InstaPost
import insta.global.type.MediaType
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.OffsetDateTime
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import tistory.adapter.out.db.TistoryRepository
import tistory.adapter.out.db.TistoryUploadFailLogRepository
import tistory.application.model.TistoryService
import tistory.application.model.selenium.tistory.TistoryNewPostPage

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TistoryService::class])
class TistoryServiceTest {
    @MockBean
    lateinit var tistoryNewPostPage: TistoryNewPostPage

    @MockBean
    lateinit var instaRepository: InstaRepository

    @MockBean
    lateinit var tistoryRepository: TistoryRepository

    @MockBean
    lateinit var instaService: InstaService

    @MockBean
    lateinit var tistoryUploadFailLogRepository: TistoryUploadFailLogRepository

    @Autowired
    lateinit var tistoryService: TistoryService

    @Test
    fun `인스타 글이 파싱되어야 함`() {
        // given
        val instaPost = InstaPost(
            instaId = "",
            caption = """
                [diary]
                <this was a fantastic day>
                hello I met kanye west and that was amazing.
                I hope you like kanye.
                #kanye #west #awesome
            """.trimIndent(),
            mediaUrl = "",
            permalink = "",
            timestamp = OffsetDateTime.now(),
            mediaType = MediaType.IMAGE,
        )

        val tistoryRequestDTO = tistoryService.parseInstaPost(instaPost)
        assertThat(tistoryRequestDTO.category).isEqualTo("diary")
        assertThat(tistoryRequestDTO.title).isEqualTo("this was a fantastic day")
        assertThat(tistoryRequestDTO.content).isEqualTo("hello I met kanye west and that was amazing.\nI hope you like kanye.\n")
        with(tistoryRequestDTO.tags.split("|")) {
            assertThat(this[0]).isEqualTo("kanye")
            assertThat(this[1]).isEqualTo("west")
            assertThat(this[2]).isEqualTo("awesome")
        }
    }

    @Test
    fun `html 변환 되어야 함`() {
        val content = """
            hello I met kanye west and that was amazing.
            I hope you like kanye.
        """.trimIndent()

        val imageUrl = "https://image.com/"
        val htmlContent = tistoryService.toHtmlContent(content, imageUrl)
        logger.info { htmlContent }

        assertThat(htmlContent).isEqualTo("<img src=\"https://image.com/\" width=\"700\"/><br/><br/><p>hello I met kanye west and that was amazing.</p><p></p><p>I hope you like kanye.</p><p></p>")
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
