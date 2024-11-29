package tistory.application.model.selenium

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
abstract class BaseSeleniumPage {
    @Lazy
    @Autowired
    lateinit var webDriverManager: WebDriverManager

    fun setUp() {
        PageFactory.initElements(webDriverManager.getWebDriver(), this)
    }

    fun WebElement.clickWhenLoaded() {
        webDriverManager.getWait().until(ExpectedConditions.elementToBeClickable(this))
        this.click()
    }

    fun By.clickWhenLoaded() {
        webDriverManager.getWait().until(ExpectedConditions.elementToBeClickable(this))
        webDriverManager.getWebDriver().findElement(this).click()
    }

    fun WebElement.sendKeysWhenLoaded(string: String) {
        webDriverManager.getWait().until(ExpectedConditions.elementToBeClickable(this))
        this.sendKeys(string)
    }

    fun By.sendKeysWhenLoaded(string: String) {
        webDriverManager.getWait().until(ExpectedConditions.presenceOfElementLocated(this))
        webDriverManager.getWebDriver().findElement(this).sendKeys(string)
    }
}
