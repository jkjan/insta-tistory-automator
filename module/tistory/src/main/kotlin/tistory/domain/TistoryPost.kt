package tistory.domain

import common.global.const.EXTRA_SMALL_TEXT_LENGTH
import common.global.const.LARGE_TEXT_LENGTH
import common.global.const.MAX_URL_LENGTH
import common.global.const.MIDDLE_TEXT_LENGTH
import common.global.const.SMALL_TEXT_LENGTH
import tistory.global.type.UploadStatus
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

    @Column(name = "insta_id", length = EXTRA_SMALL_TEXT_LENGTH)
    var instaId: String,

    var uploadTimestamp: OffsetDateTime? = null,

    @Column(length = MAX_URL_LENGTH)
    var tistoryUrl: String? = null,

    @Column(length = SMALL_TEXT_LENGTH)
    var title: String,

    @Column(length = LARGE_TEXT_LENGTH)
    var content: String,

    @Column(length = SMALL_TEXT_LENGTH)
    var category: String,

    @Column(length = MIDDLE_TEXT_LENGTH)
    var tags: String,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var uploadStatus: UploadStatus? = null,

    var fetchedTimestamp: OffsetDateTime? = null,
)
