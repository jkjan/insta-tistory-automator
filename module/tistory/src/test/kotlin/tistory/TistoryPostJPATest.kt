package tistory

import kotlin.test.Test
import kotlin.test.assertContains
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import tistory.adapter.out.db.TistoryRepository
import tistory.domain.TistoryPost

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TistoryPostJPATest {
    @Autowired
    lateinit var tistoryRepository: TistoryRepository

    @Test
    fun `tags 삽입`() {
        // given
        val tistoryPost = TistoryPost(
            instaId = "",
            category = "",
            title = "",
            content = "",
            tags = "tag1|tag2|tag3",
        )
        val uploadedTistoryPost = tistoryRepository.save(tistoryPost)

        assertDoesNotThrow {
            val fetchedTistoryPost = tistoryRepository.findById(uploadedTistoryPost.id)
            with(fetchedTistoryPost.get().tags.split("|")) {
                assertContains(this[0], "tag1")
                assertContains(this[1], "tag2")
                assertContains(this[2], "tag3")
            }
        }
    }
}
