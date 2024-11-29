package insta.adapter.out.db

import insta.domain.InstaPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["insta.adapter.out.db"])
interface InstaRepository : JpaRepository<InstaPost, String> {
    fun findFirstByOrderByTimestampDesc(): InstaPost?
    fun findByTistoryFetched(tistoryFetched: Boolean): List<InstaPost>
}
