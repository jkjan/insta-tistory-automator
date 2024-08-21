package com.jun.instatistoryautomatorserver.global.config

import com.jun.instatistoryautomatorserver.adapter.out.api.InstaApi
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class RetrofitConfig {
    @Bean(name=["insta"])
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Bean
    fun instaApi(@Qualifier("insta") retrofit: Retrofit): InstaApi =
        retrofit.create(InstaApi::class.java)
}