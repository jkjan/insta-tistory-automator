package com.jun.instatistoryautomatorserver.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes
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

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var mediaType: MediaType? = null,

    var fetchedTimestamp: OffsetDateTime? = null,
)