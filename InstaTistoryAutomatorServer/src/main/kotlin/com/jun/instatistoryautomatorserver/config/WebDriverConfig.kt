package com.jun.instatistoryautomatorserver.config

import com.jun.instatistoryautomatorserver.property.WebDriverProperty
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@EnableConfigurationProperties(WebDriverProperty::class)
class WebDriverConfig(private val webDriverProperty: WebDriverProperty) {
    @Bean
    fun chromeDriver(): ChromeDriver {
        val chromeOptions = ChromeOptions()

        webDriverProperty.arguments.forEach {
            chromeOptions.addArguments(it)
        }

        val driver = ChromeDriver(chromeOptions)
        return driver
    }

    @Bean
    fun waitConfig(driver: ChromeDriver): Wait<WebDriver> =
        WebDriverWait(driver, Duration.ofSeconds(webDriverProperty.timeout))
}