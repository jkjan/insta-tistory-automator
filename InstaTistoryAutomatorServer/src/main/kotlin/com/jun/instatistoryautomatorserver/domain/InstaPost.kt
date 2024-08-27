package com.jun.instatistoryautomatorserver.domain

import com.jun.instatistoryautomatorserver.global.type.MediaType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.time.OffsetDateTime
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

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
