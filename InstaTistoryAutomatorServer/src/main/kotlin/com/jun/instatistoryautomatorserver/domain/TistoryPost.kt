package com.jun.instatistoryautomatorserver.domain

import com.jun.instatistoryautomatorserver.global.type.UploadStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.time.OffsetDateTime
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
class TistoryPost(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tistory_post_id_gen")
    @SequenceGenerator(name = "tistory_post_id_gen", sequenceName = "tistory_post_tistory_id_seq", allocationSize = 1)
    @Column(name = "tistory_id", nullable = false)
    var id: Int = 0,

    @Column(name = "insta_id", length = 64)
    var instaId: String? = null,

    var uploadTimestamp: OffsetDateTime? = null,

    @Column(length = 2083)
    var tistoryUrl: String? = null,

    @Column(length = 256)
    var title: String? = null,

    @Column(length = Integer.MAX_VALUE)
    var content: String? = null,

    @Column(length = 256)
    var category: String? = null,

    var tags: String? = null,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var uploadStatus: UploadStatus? = null,

    var fetchedTimestamp: OffsetDateTime? = null,
)
