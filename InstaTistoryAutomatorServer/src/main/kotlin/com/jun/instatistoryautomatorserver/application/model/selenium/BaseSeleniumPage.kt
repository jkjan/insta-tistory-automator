package com.jun.instatistoryautomatorserver.application.model.selenium

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Wait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class BaseSeleniumPage {
    @Autowired
    lateinit var wait: Wait<WebDriver>

    @Autowired
    lateinit var driver: WebDriver

    fun setUp() {
        PageFactory.initElements(driver, this)
    }

    fun WebElement.clickWhenLoaded() {
        wait.until(ExpectedConditions.elementToBeClickable(this))
        this.click()
    }

    fun By.clickWhenLoaded() {
        wait.until(ExpectedConditions.elementToBeClickable(this))
        driver.findElement(this).click()
    }

    fun WebElement.sendKeysWhenLoaded(string: String) {
        wait.until(ExpectedConditions.elementToBeClickable(this))
        this.sendKeys(string)
    }

    fun By.sendKeysWhenLoaded(string: String) {
        wait.until(ExpectedConditions.presenceOfElementLocated(this))
        driver.findElement(this).sendKeys(string)
    }
}
