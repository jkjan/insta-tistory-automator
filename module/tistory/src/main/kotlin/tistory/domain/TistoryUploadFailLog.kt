package tistory.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "tistory_upload_fail_log")
class TistoryUploadFailLog(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tistory_upload_fail_log_id_gen")
    @SequenceGenerator(
        name = "tistory_upload_fail_log_id_gen",
        sequenceName = "tistory_upload_fail_log_log_id_seq",
        allocationSize = 1,
    )
    @Column(name = "log_id", nullable = false)
    var id: Int = 0,

    @Column(name = "tistory_id")
    var tistoryId: Int,

    @Column(name = "reason", length = Integer.MAX_VALUE)
    var reason: String,

    @Column(name = "retry_timestamp")
    var retryTimestamp: OffsetDateTime? = null,
)
