package com.jun.instatistoryautomatorserver.global.config

import com.jun.instatistoryautomatorserver.application.model.InstaTistoryAutomatorService
import com.jun.instatistoryautomatorserver.global.property.ScheduleProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.scheduling.support.CronTrigger

@Configuration
@EnableScheduling
@EnableConfigurationProperties(ScheduleProperty::class)
class ScheduleConfiguration(
    private val properties: ScheduleProperty,
    private val instaTistoryAutomatorService: InstaTistoryAutomatorService,
) : SchedulingConfigurer {
    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.scheduler?.schedule(
            instaTistoryAutomatorService::fetchFromInstaAndUploadToTistory,
            CronTrigger(properties.cron[INSTA_TISTORY_AUTOMATOR] ?: "0 0 11 * * *"),
        )
    }

    companion object {
        const val INSTA_TISTORY_AUTOMATOR = "insta-tistory-automator-server"
    }
}
