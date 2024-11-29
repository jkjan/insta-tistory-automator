package tistory.adapter.out.db

import tistory.domain.TistoryUploadFailLog
import org.springframework.data.jpa.repository.JpaRepository

interface TistoryUploadFailLogRepository : JpaRepository<TistoryUploadFailLog, Int> {
    fun findByTistoryId(tistoryId: Int): TistoryUploadFailLog
}
