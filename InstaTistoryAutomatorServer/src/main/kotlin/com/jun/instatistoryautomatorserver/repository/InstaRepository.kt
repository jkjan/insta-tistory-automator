package com.jun.instatistoryautomatorserver.repository

import com.jun.instatistoryautomatorserver.entity.InstaPost
import org.springframework.data.jpa.repository.JpaRepository

interface InstaRepository: JpaRepository<InstaPost, String> {
    fun findFirstByOrderByTimestampDesc(): InstaPost?
}