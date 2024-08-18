package com.jun.instatistoryautomatorserver.config

import com.jun.instatistoryautomatorserver.dto.InstaApi
import com.jun.instatistoryautomatorserver.property.InstaProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class RetrofitConfig(private val instaProperty: InstaProperty) {
    @Bean(name=["insta"])
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(instaProperty.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Bean
    fun instaApi(@Qualifier("insta") retrofit: Retrofit): InstaApi =
        retrofit.create(InstaApi::class.java)
}