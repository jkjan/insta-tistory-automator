package com.jun.instatistoryautomatorserver.insta

import com.jun.instatistoryautomatorserver.config.RetrofitConfig
import com.jun.instatistoryautomatorserver.property.InstaProperty
import com.jun.instatistoryautomatorserver.repository.InstaRepository
import com.jun.instatistoryautomatorserver.service.InstaService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import

@TestConfiguration
@Import(InstaService::class, RetrofitConfig::class)
class InstaTestConfig {
    @MockBean
    private lateinit var instaRepository: InstaRepository

    @MockBean
    private lateinit var instaProperty: InstaProperty
}