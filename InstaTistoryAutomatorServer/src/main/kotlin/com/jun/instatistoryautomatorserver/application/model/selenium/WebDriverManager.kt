package com.jun.instatistoryautomatorserver.application.model.selenium

import com.jun.instatistoryautomatorserver.global.exception.ChromeDriverNotFoundException
import com.jun.instatistoryautomatorserver.global.property.WebDriverProperty
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.io.File
import java.io.FileOutputStream
import java.time.Duration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(WebDriverProperty::class)
class WebDriverManager(private val webDriverProperty: WebDriverProperty) {
    @Value("\${user.dir")
    private lateinit var rootPath: String

    private var webDriver: WebDriver? = null
    private var wait: Wait<WebDriver>? = null

    @PostConstruct
    fun initialize() {
        try {
            copyChromeDriverFile()
            setWebDriver()
        } catch (e: Exception) {
            throw ChromeDriverNotFoundException("ChromeDriver가 없습니다.", e)
        }
    }

    @PreDestroy
    fun destroy() {
        webDriver?.quit()
        webDriver = null
        wait = null
    }

    fun copyChromeDriverFile() {
        val chromeDriverInputStream = ClassPathResource("chromedriver").inputStream
        val chromeDriverOutputStream = FileOutputStream("$rootPath/chromedriver-copy.exe")
        chromeDriverInputStream.copyTo(chromeDriverOutputStream)
        chromeDriverInputStream.close()
        chromeDriverOutputStream.close()
    }

    fun setWebDriver() {
        val chromeDriver = File("$rootPath/chromedriver-copy.exe")
        chromeDriver.setExecutable(true)
        chromeDriver.deleteOnExit()

        val chromeDriverService = ChromeDriverService.Builder().usingDriverExecutable(chromeDriver).build()

        webDriver = ChromeDriver(
            chromeDriverService,
            ChromeOptions()
                .addArguments(webDriverProperty.arguments)
                .setExperimentalOption("excludeSwitches", listOf("disable-popup-blocking")),
        )

        wait = WebDriverWait(webDriver, Duration.ofSeconds(webDriverProperty.timeout))
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
