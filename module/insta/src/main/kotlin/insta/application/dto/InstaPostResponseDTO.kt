package insta.application.dto

import insta.global.type.MediaType
import com.fasterxml.jackson.annotation.JsonProperty
import insta.application.model.InstaService.Companion.correct
import insta.domain.InstaPost
import java.time.OffsetDateTime

data class InstaPostResponseDTO(
    val id: String,

    @JsonProperty("media_type")
    val mediaType: MediaType,

    @JsonProperty("media_url")
    val mediaUrl: String,
    val caption: String,
    val permalink: String,
    val timestamp: String,
) {
    fun toNewInstaPost() =
        InstaPost(
            instaId = id,
            mediaUrl = mediaUrl,
            permalink = permalink,
            caption = caption,
            timestamp = OffsetDateTime.parse(timestamp.correct()),
            mediaType = mediaType,
            fetchedTimestamp = OffsetDateTime.now(),
            tistoryFetched = false,
        )
}
