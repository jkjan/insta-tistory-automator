package com.jun.instatistoryautomatorserver.service

import com.jun.instatistoryautomatorserver.YamlPropertySourceFactory
import com.jun.instatistoryautomatorserver.config.RetrofitConfig
import com.jun.instatistoryautomatorserver.config.TestConfig
import com.jun.instatistoryautomatorserver.property.InstaProperty
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.TestPropertySources

@SpringBootTest
//@ContextConfiguration(classes = [TestConfig::class, RetrofitConfig::class, InstaProperty::class])
@AutoConfigureWireMock(port = 0)
//@TestPropertySource("classpath:application.properties")
@EnableConfigurationProperties(InstaProperty::class)
class InstaServiceTest {
    @Value("\${wiremock.server.port}")
    private var port: Int = 0

    private val template = TestRestTemplate()
//
//    @Autowired
//    private lateinit var instaApi: InstaApi

    @Test
    fun fetchInstaPost() {
        val response = template.getForEntity<String>("http://localhost:$port/some/thing")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("Hello, world!")
    }
}