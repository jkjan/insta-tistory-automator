package com.jun.instatistoryautomatorserver.component

import com.jun.instatistoryautomatorserver.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.exception.LoginException
import com.jun.instatistoryautomatorserver.exception.PopupException
import com.jun.instatistoryautomatorserver.property.TistoryProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.NotFoundException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Wait
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(TistoryProperty::class)
class TistoryUploader(
    private val tistoryProperty: TistoryProperty,
    private val driver: ChromeDriver,
    private val wait: Wait<WebDriver>,
) {
    fun uploadPostToTistoryBlog(tistoryRequestDTO: TistoryRequestDTO) {
        login()
        handlePopup()
        selectCategory(tistoryRequestDTO.category)
        selectHTML()
        handlePopup(cancel = false)
        Thread.sleep(3000)
    }

    fun login() {
        driver.get(tistoryProperty.entryUrl)
        val currentUrl = driver.currentUrl
        if (!currentUrl.contains("tistory")) return

        try {
            val loginLink = driver.findElement(By.className("btn_login"))
            loginLink.click()

            val email = driver.findElement(By.id("loginId--1"))
            email.sendKeys(tistoryProperty.email)

            val password = driver.findElement(By.id("password--2"))
            password.sendKeys(tistoryProperty.password)

            val button = driver.findElement(By.className("submit"))
            button.click()
        } catch (e: Exception) {
            throw LoginException("로그인 실패", e)
        }
    }

    fun handlePopup(cancel: Boolean = true) {
        try {
            wait.until(ExpectedConditions.alertIsPresent())

            if (cancel)
                driver.switchTo().alert()?.dismiss()
            else
                driver.switchTo().alert()?.accept()

            driver.switchTo().defaultContent()
        } catch (e: Exception) {
            if (e is TimeoutException) {
                logger.info { }
                return
            }
            else {
                throw PopupException("팝업 해제 실패", e)
            }
        }
    }

    fun selectCategory(targetCategory: String) {
        val categoryButtonLocator = By.xpath("//button[@id='category-btn']")
        categoryButtonLocator.click()

        var categoryIndex = 1

        while (true) {
            try {
                val categoryLocator = By.xpath("(//div[@id='category-list']//span)[${categoryIndex++}]")
                val category = driver.findElement(categoryLocator)

                if (category.text.contains(targetCategory)) {
                    category.click()
                    break
                }
            } catch (e: NotFoundException) {
                val noCategorySelector = By.xpath("(//div[@id='category-list']//span)[1]")
                noCategorySelector.click()
            }
        }
    }

    fun selectHTML() {
        val modeSelector = By.xpath("//button[@id='editor-mode-layer-btn-open']")
        modeSelector.click()

        val htmlSelector = By.xpath("//div[@id='editor-mode-html']")
        htmlSelector.click()
    }

    fun By.click() {
        wait.until(ExpectedConditions.elementToBeClickable(this))
        driver.findElement(this).click()
    }

    companion object {
        val logger = KotlinLogging.logger {  }
    }
}