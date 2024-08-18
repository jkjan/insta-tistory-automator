package com.jun.instatistoryautomatorserver.component

import com.jun.instatistoryautomatorserver.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.exception.LoginException
import com.jun.instatistoryautomatorserver.property.TistoryLoginProperty
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Wait
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.sql.Time

@Component
@EnableConfigurationProperties(TistoryLoginProperty::class)
class TistoryUploader(
    private val loginProperty: TistoryLoginProperty,
    private val driver: ChromeDriver,
    private val wait: Wait<WebDriver>,
) {
    fun uploadPostToTistoryBlog(tistoryRequestDTO: TistoryRequestDTO) {
        driver.get(POST_ENTRY_URL)
        val currentUrl = driver.currentUrl

        // 로그인 절차
        if (currentUrl.contains("tistory")) {
            login()
        }

        // 이전 글 팝업 해제
        wait.until(ExpectedConditions.alertIsPresent())
        driver.switchTo().alert()?.dismiss()
        driver.switchTo().defaultContent()

        // 본문
        // 1. 카테고리
        driver.findElement(By.id("category-btn")).click()
        val options = driver.findElements(By.xpath("//div[@id='category-list']//span"))
        println(options)
        for (option in options) {
            println(option)
            wait.until(ExpectedConditions.elementToBeClickable(option))
            val category = option.text
            category.contains(tistoryRequestDTO.category)
            option.click()
        }
    }

    fun login() {
        try {
            val loginLink = driver.findElement(By.className("btn_login"))
            loginLink.click()

            val email = driver.findElement(By.id("loginId--1"))
            email.sendKeys(loginProperty.email)

            val password = driver.findElement(By.id("password--2"))
            password.sendKeys(loginProperty.password)

            val button = driver.findElement(By.className("submit"))
            button.click()
        } catch (e: Exception) {
            throw LoginException("로그인 실패", e)
        }
    }

    companion object {
        const val POST_ENTRY_URL = "https://www.tistory.com/auth/login?redirectUrl=https%3A%2F%2Fnowhereland.tistory.com%2Fmanage%2Fnewpost%2F"
        const val BLOG_NAME = "nowhereland"
    }
}