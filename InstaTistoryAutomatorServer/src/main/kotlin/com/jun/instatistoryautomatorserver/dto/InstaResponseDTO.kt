package com.jun.instatistoryautomatorserver.dto

import com.google.gson.annotations.SerializedName
import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.entity.MediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.time.OffsetDateTime

interface InstaApi {
    @GET
    suspend fun getInstaPosts(@Url url: String): Response<InstaResponseDTO>
}

data class InstaResponseDTO (
    var `data`: List<InstaPostDTO>,
    var paging: Paging?,
)

data class InstaPostDTO(
    var id: String?,

    @SerializedName("media_type")
    var mediaType: MediaType?,

    @SerializedName("media_url")
    var mediaUrl: String?,
    var caption: String?,
    var permalink: String?,
    var timestamp: String?,
) {
    fun toInstaPost() =
        InstaPost(
            instaId = id,
            mediaUrl = mediaUrl,
            permalink = permalink,
            caption = caption,
            timestamp = OffsetDateTime.parse(timestamp!!.correct()),
            mediaType = mediaType,
            fetchedTimestamp = OffsetDateTime.now()
        )
}

data class Paging(
    var cursors: Any?,
    var previous: String?,
    var next: String?,
)

fun String.correct(): String {
    return StringBuilder(this).apply { insert(22, ':') }.toString()
}
