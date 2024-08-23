package com.jun.instatistoryautomatorserver

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InstaTistoryAutomatorServerApplication {
    fun main(args: Array<String>) {
        runApplication<InstaTistoryAutomatorServerApplication>(*args)
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
