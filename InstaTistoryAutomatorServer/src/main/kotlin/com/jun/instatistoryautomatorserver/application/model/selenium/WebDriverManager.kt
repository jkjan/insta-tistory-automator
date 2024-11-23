package com.jun.instatistoryautomatorserver.application.model.selenium

import com.jun.instatistoryautomatorserver.global.exception.ChromeDriverNotFoundException
import com.jun.instatistoryautomatorserver.global.property.WebDriverProperty
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.io.File
import java.net.URI
import java.time.Duration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(WebDriverProperty::class)
class WebDriverManager(private val webDriverProperty: WebDriverProperty) {
    private var webDriver: WebDriver? = null
    private var wait: Wait<WebDriver>? = null

    @PostConstruct
    fun initialize() {
        try {
            if (webDriverProperty.realChromeEnabled) {
                setWebDriverWithChrome()
            } else {
                setWebDriverWithRemote()
            }

            wait = WebDriverWait(webDriver, Duration.ofSeconds(webDriverProperty.timeout))
        } catch (e: Exception) {
            throw ChromeDriverNotFoundException("ChromeDriver 세팅에 실패했습니다.", e)
        }
    }

    @PreDestroy
    fun destroy() {
        webDriver?.quit()
        webDriver = null
        wait = null
    }

    fun setWebDriverWithChrome() {
        val chromeDriver = File(webDriverProperty.chromeDriverPath)

        val chromeDriverService = ChromeDriverService.Builder().usingDriverExecutable(chromeDriver).build()

        webDriver = ChromeDriver(
            chromeDriverService,
            ChromeOptions()
                .addArguments(webDriverProperty.arguments)
                .setExperimentalOption("excludeSwitches", listOf("disable-popup-blocking")),
        )
    }

    fun setWebDriverWithRemote() {
        webDriver = RemoteWebDriver(
            URI(webDriverProperty.remoteUrl).toURL(),
            ChromeOptions()
                .addArguments(webDriverProperty.arguments)
                .setExperimentalOption("excludeSwitches", listOf("disable-popup-blocking")),
        )
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
