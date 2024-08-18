package com.jun.instatistoryautomatorserver.component

import com.jun.instatistoryautomatorserver.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.exception.TistoryException
import com.jun.instatistoryautomatorserver.property.TistoryProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.Keys
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
        with(tistoryRequestDTO) {
            login()
            handlePopup()
            selectCategory(category)
            selectHTML()
            handlePopup(cancel = false)
            setTitle(title)
            setContent(content)
            setTags(tags)
            submit()
        }
    }

    fun login() {
        driver.get(tistoryProperty.entryUrl)
        val currentUrl = driver.currentUrl

        if (!currentUrl.contains("tistory")) return

        val loginLocator = By.className("btn_login")
        loginLocator.click()

        val emailLocator = By.xpath("(//input)[1]")
        emailLocator.write(tistoryProperty.email)

        val passwordLocator = By.xpath("(//input)[2]")
        passwordLocator.write(tistoryProperty.password)

        val buttonLocator = By.xpath("//button[contains(@class, 'submit')]")
        buttonLocator.click()
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
                logger.info { "이전 글 팝업 없음" }
                return
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
                    logger.info { "카테고리 번호: $categoryIndex, ${category.text} 으로 작성" }
                    category.click()
                    break
                }
            } catch (e: NotFoundException) {
                logger.error { "카테고리 없음: $targetCategory 으로 대체" }
                val noCategorySelector = By.xpath("(//div[@id='category-list']//span)[1]")
                noCategorySelector.click()
                break
            }
        }
    }

    fun selectHTML() {
        val modeSelector = By.xpath("//button[@id='editor-mode-layer-btn-open']")
        modeSelector.click()

        val htmlSelector = By.xpath("//div[@id='editor-mode-html']")
        htmlSelector.click()
    }

    fun setTitle(title: String) {
        val titleLocator = By.xpath("//textarea[@id='post-title-inp']")
        titleLocator.write(title)
    }

    fun setContent(content: String) {
        val codeMirrorLocator = By.xpath("(//div[contains(@class, 'CodeMirror')]//pre[contains(@class, 'CodeMirror-line')])[1]")
        codeMirrorLocator.click()

        val codeTextAreaLocator = By.xpath("//div[contains(@class, 'CodeMirror')]//textarea")
        codeTextAreaLocator.write(content)
    }

    fun setTags(tags: List<String>) {
        tags.forEach { tag ->
            val tagInputLocator = By.xpath("//input[@id='tagText']")
            tagInputLocator.write(tag)
            val tagInput = driver.findElement(tagInputLocator)
            tagInput.sendKeys(Keys.ENTER)
        }
    }

    fun submit() {
        val publishLayerButtonLocator = By.xpath("//button[@id='publish-layer-btn']")
        publishLayerButtonLocator.click()

        val publishButtonLocator = By.xpath("//button[@id='publish-btn']")
        publishButtonLocator.click()
    }

    fun By.click() {
        wait.until(ExpectedConditions.elementToBeClickable(this))
        driver.findElement(this).click()
    }

    fun By.write(string: String) {
        wait.until(ExpectedConditions.presenceOfElementLocated(this))
        driver.findElement(this).sendKeys(string)
    }

    companion object {
        val logger = KotlinLogging.logger {  }
    }
}