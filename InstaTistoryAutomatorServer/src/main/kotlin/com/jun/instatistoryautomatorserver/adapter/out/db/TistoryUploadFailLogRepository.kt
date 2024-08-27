package com.jun.instatistoryautomatorserver.adapter.out.db

import com.jun.instatistoryautomatorserver.domain.TistoryUploadFailLog
import org.springframework.data.jpa.repository.JpaRepository

interface TistoryUploadFailLogRepository : JpaRepository<TistoryUploadFailLog, Int> {
    fun findByTistoryId(tistoryId: Int): TistoryUploadFailLog
}
