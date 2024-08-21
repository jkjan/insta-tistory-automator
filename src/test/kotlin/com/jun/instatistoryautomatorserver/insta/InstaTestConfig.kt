package com.jun.instatistoryautomatorserver.insta

import com.jun.instatistoryautomatorserver.adapter.out.db.InstaRepository
import com.jun.instatistoryautomatorserver.application.model.InstaService
import com.jun.instatistoryautomatorserver.global.config.RetrofitConfig
import com.jun.instatistoryautomatorserver.global.property.InstaProperty
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
