package com.jun.instatistoryautomatorserver.application.model.selenium

import com.jun.instatistoryautomatorserver.global.property.WebDriverProperty
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.time.Duration
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Lazy
@Component
@EnableConfigurationProperties(WebDriverProperty::class)
class WebDriverManager(private val webDriverProperty: WebDriverProperty) {
    init {
        val rootPath = System.getProperty("user.dir")
        val chromeDriverPath = "$rootPath/${webDriverProperty.chromeDriverPath}"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
    }

    private var webDriver: WebDriver? = null
    private var wait: Wait<WebDriver>? = null

    @PostConstruct
    fun initialize() {
        webDriver = ChromeDriver(
            ChromeOptions()
                .addArguments(webDriverProperty.arguments)
                .setExperimentalOption("excludeSwitches", listOf("disable-popup-blocking")),
        )

        wait = WebDriverWait(webDriver, Duration.ofSeconds(webDriverProperty.timeout))
    }

    @PreDestroy
    fun destroy() {
        webDriver?.quit()
        webDriver = null
        wait = null
    }

    fun getWebDriver(): WebDriver {
        if (webDriver == null) {
            initialize()
        }

        return webDriver!!
    }

    fun getWait(): Wait<WebDriver> {
        if (wait == null) {
            initialize()
        }

        return wait!!
    }
}
