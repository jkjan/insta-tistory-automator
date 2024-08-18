package com.jun.instatistoryautomatorserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.time.OffsetDateTime


enum class MediaType {
    IMAGE,
    VIDEO,
    CAROUSEL_ALBUM,
}

@Entity
class InstaPost(
    @Id
    @Column(nullable = false, length = 64)
    var instaId: String? = null,

    @Column(length = 2083)
    var mediaUrl: String? = null,

    @Column(length = 2083)
    var permalink: String? = null,

    var caption: String? = null,

    var timestamp: OffsetDateTime? = null,

    var mediaType: MediaType? = null,

    var fetchedTimestamp: OffsetDateTime? = null,
)