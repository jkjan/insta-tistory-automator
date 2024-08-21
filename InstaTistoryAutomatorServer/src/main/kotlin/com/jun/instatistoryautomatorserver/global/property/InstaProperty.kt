package com.jun.instatistoryautomatorserver.global.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "insta")
data class InstaProperty (
    val baseUrl: String,
    val apiVersion: String,
    val userId: String,
    val userToken: String,
    val mediaFields: List<String>,
)