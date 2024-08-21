package com.jun.instatistoryautomatorserver.adapter.out.api

import com.jun.instatistoryautomatorserver.application.dto.InstaApiResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface InstaApi {
    @GET
    suspend fun getInstaPosts(
        @Url url: String,
    ): Response<InstaApiResponseDTO>
}
