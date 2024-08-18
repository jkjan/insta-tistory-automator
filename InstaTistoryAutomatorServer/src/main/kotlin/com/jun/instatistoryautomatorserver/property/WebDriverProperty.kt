package com.jun.instatistoryautomatorserver.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "webdriver")
data class WebDriverProperty(
    val timeout: Long = 3,
    val arguments: List<String> = listOf(),
    val chromeDriverPath: String,
)