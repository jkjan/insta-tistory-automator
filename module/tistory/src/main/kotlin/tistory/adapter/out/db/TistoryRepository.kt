package tistory.adapter.out.db

import tistory.domain.TistoryPost
import tistory.global.type.UploadStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TistoryRepository : JpaRepository<TistoryPost, Int> {
    fun findFirstByUploadStatusNotOrderByFetchedTimestamp(uploadStatus: UploadStatus): List<TistoryPost>
}
