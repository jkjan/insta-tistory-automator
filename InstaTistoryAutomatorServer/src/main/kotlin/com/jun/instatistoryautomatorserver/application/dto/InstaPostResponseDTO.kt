package com.jun.instatistoryautomatorserver.application.dto

import com.google.gson.annotations.SerializedName
import com.jun.instatistoryautomatorserver.global.type.MediaType

data class InstaPostResponseDTO(
    var id: String?,

    @SerializedName("media_type")
    var mediaType: MediaType?,

    @SerializedName("media_url")
    var mediaUrl: String?,
    var caption: String?,
    var permalink: String?,
    var timestamp: String?,
)
