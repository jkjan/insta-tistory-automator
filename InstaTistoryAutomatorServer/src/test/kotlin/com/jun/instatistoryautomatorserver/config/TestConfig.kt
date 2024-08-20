package com.jun.instatistoryautomatorserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.Environment

@TestConfiguration
class TestConfig {
    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun propertySourcesPlaceHolderConfigurer() =
        PropertySourcesPlaceholderConfigurer()
}