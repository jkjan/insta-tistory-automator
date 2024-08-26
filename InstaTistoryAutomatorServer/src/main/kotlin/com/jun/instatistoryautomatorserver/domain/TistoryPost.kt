package com.jun.instatistoryautomatorserver.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "tistory_post")
class TistoryPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tistory_post_id_gen")
    @SequenceGenerator(name = "tistory_post_id_gen", sequenceName = "tistory_post_tistory_id_seq", allocationSize = 1)
    @Column(name = "tistory_id", nullable = false)
    var id: Int? = null

    @Column(name = "insta_id", length = 64)
    var instaId: String? = null

    @Column(name = "upload_timestamp")
    var uploadTimestamp: Instant? = null

    @Column(name = "tistory_url", length = 2083)
    var tistoryUrl: String? = null

    @Column(name = "title", length = 256)
    var title: String? = null

    @Column(name = "content", length = Integer.MAX_VALUE)
    var content: String? = null

    @Column(name = "category", length = 256)
    var category: String? = null

    /*
         TODO [Reverse Engineering] create field to map the 'tags' column
         Available actions: Define target Java type | Uncomment as is | Remove column mapping
            @Column(name = "tags", columnDefinition = "varchar [](256)")
            var tags: Any? = null
        */
}
