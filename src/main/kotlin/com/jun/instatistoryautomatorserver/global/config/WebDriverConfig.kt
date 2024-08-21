package com.jun.instatistoryautomatorserver.global.config

import com.jun.instatistoryautomatorserver.global.property.WebDriverProperty
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
    init {
        val rootPath = System.getProperty("user.dir")
        val chromeDriverPath = "$rootPath/${webDriverProperty.chromeDriverPath}"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
    }

    @Bean(destroyMethod = "quit")
    fun chromeDriver(): ChromeDriver =
        ChromeDriver(
            ChromeOptions()
                .addArguments(webDriverProperty.arguments)
                .setExperimentalOption("excludeSwitches", listOf("disable-popup-blocking")),
        )

    @Bean
    fun waitConfig(driver: ChromeDriver): Wait<WebDriver> = WebDriverWait(driver, Duration.ofSeconds(webDriverProperty.timeout))
}
