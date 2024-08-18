package com.jun.instatistoryautomatorserver.dto

import com.jun.instatistoryautomatorserver.entity.MediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface InstaApi {
    @GET("{apiVersion}/{userId}/media")
    fun getInstaPosts(
        @Path("apiVersion") apiVersion: String,
        @Path("userId") userId: String,
        @QueryMap options: Map<String, String>,
    ): InstaResponseDTO

    @GET
    suspend fun getInstaPosts(@Url url: String): Response<InstaResponseDTO>
}

data class InstaResponseDTO (
    var `data`: List<InstaPostDTO>,
    var paging: Paging,
)

data class InstaPostDTO(
    var id: String?,
    var mediaType: MediaType?,
    var mediaUrl: String?,
    var caption: String?,
    var permalink: String?,
    var timestamp: String?,
)

data class Paging(
    var cursors: Any?,
    var previous: String?,
    var next: String?,
)
