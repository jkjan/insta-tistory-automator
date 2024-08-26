package com.jun.instatistoryautomatorserver.adapter.out.db

import com.jun.instatistoryautomatorserver.domain.TistoryPost
import com.jun.instatistoryautomatorserver.global.type.UploadStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TistoryRepository : JpaRepository<TistoryPost, Int> {
    fun findByUploadStatusNot(uploadStatus: UploadStatus): List<TistoryPost>
}
