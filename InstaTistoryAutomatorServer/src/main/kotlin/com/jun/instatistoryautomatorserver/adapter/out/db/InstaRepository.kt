package com.jun.instatistoryautomatorserver.adapter.out.db

import com.jun.instatistoryautomatorserver.domain.InstaPost
import org.springframework.data.jpa.repository.JpaRepository

interface InstaRepository : JpaRepository<InstaPost, String> {
    fun findFirstByOrderByTimestampDesc(): InstaPost?
}
