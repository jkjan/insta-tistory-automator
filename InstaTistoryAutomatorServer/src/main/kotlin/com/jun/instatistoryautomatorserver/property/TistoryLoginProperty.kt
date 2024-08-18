package com.jun.instatistoryautomatorserver.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@ConfigurationProperties(prefix = "tistory-login")
data class TistoryLoginProperty(
    val email: String,
    val password: String,
)