package com.jun.instatistoryautomatorserver.global.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "schedule")
data class ScheduleProperty(
    val cron: Map<String, String>,
)
