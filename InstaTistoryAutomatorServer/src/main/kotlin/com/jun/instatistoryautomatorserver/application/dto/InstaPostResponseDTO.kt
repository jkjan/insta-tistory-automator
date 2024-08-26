package com.jun.instatistoryautomatorserver.application.dto

import com.google.gson.annotations.SerializedName
import com.jun.instatistoryautomatorserver.application.model.InstaService.Companion.correct
import com.jun.instatistoryautomatorserver.domain.InstaPost
import com.jun.instatistoryautomatorserver.global.type.MediaType
import java.time.OffsetDateTime

data class InstaPostResponseDTO(
    var id: String?,

    @SerializedName("media_type")
    var mediaType: MediaType?,

    @SerializedName("media_url")
    var mediaUrl: String?,
    var caption: String?,
    var permalink: String?,
    var timestamp: String?,
) {
    fun toNewInstaPost() =
        InstaPost(
            instaId = id,
            mediaUrl = mediaUrl,
            permalink = permalink,
            caption = caption,
            timestamp = OffsetDateTime.parse(timestamp!!.correct()),
            mediaType = mediaType,
            fetchedTimestamp = OffsetDateTime.now(),
            tistoryFetched = false,
        )
}
