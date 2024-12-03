package insta.domain

import common.global.const.LARGE_TEXT_LENGTH
import common.global.const.MAX_URL_LENGTH
import insta.global.type.MediaType
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
    var instaId: String,

    @Column(length = MAX_URL_LENGTH)
    var mediaUrl: String,

    @Column(length = MAX_URL_LENGTH)
    var permalink: String,

    @Column(length = LARGE_TEXT_LENGTH)
    var caption: String,

    var timestamp: OffsetDateTime,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var mediaType: MediaType,

    var fetchedTimestamp: OffsetDateTime? = null,

    var tistoryFetched: Boolean = false,
)
