package com.jun.instatistoryautomatorserver.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tistory")
data class TistoryProperty(
    val entryUrl: String,
    val email: String,
    val password: String,
)