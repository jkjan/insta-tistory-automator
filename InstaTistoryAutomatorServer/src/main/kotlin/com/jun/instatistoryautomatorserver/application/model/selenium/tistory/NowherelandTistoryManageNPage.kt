package com.jun.instatistoryautomatorserver.application.model.selenium.tistory

import com.jun.instatistoryautomatorserver.InstaTistoryAutomatorServerApplication.Companion.logger
import com.jun.instatistoryautomatorserver.application.model.selenium.BaseSeleniumPage
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.global.exception.TistoryException
import org.openqa.selenium.By
import org.openqa.selenium.NotFoundException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.stereotype.Component

// page_url = https://nowhereland.tistory.com/manage/newpost/
@Component
class NowherelandTistoryManageNPage(
    private val accountsKakaoLoginPage: AccountsKakaoLoginPage,
) : BaseSeleniumPage() {
    @FindBy(xpath = "//*[@id='category-btn']")
    lateinit var buttonCategory: WebElement

    @FindBy(xpath = "//*[@id='post-title-inp']")
    lateinit var textareaPostTitleInp: WebElement

    @FindBy(xpath = "//*[@id='editor-mode-layer-btn-open']")
    lateinit var buttonEditorModeLayerOpen: WebElement

    @FindBy(xpath = "//*[@id='tagText']")
    lateinit var inputTagText: WebElement

    @FindBy(xpath = "(//div[contains(@class, 'CodeMirror')]//pre[contains(@class, 'CodeMirror-line')])[1]")
    lateinit var preCodeLine: WebElement

    @FindBy(xpath = "//*[@id='publish-layer-btn']")
    lateinit var buttonPublishLayer: WebElement

    @ThrowWithMessage("로그인 에러", TistoryException::class)
    override fun setUp() {
        accountsKakaoLoginPage.login()
        super.setUp()
    }

    @ThrowWithMessage("팝업 에러", TistoryException::class)
    fun handlePopup(cancel: Boolean = true) {
        try {
            wait.until(ExpectedConditions.alertIsPresent())

            if (cancel) {
                driver.switchTo().alert()?.dismiss()
            } else {
                driver.switchTo().alert()?.accept()
            }

            driver.switchTo().defaultContent()
        } catch (e: TimeoutException) {
            logger.info(e) { "팝업 없음" }
            return
        }
    }

    @ThrowWithMessage("카테고리 에러", TistoryException::class)
    fun setCategory(targetCategory: String) {
        buttonCategory.clickWhenLoaded()

        var categoryIndex = 1

        while (true) {
            try {
                val categoryLocator = By.xpath("(//div[@id='category-list']//span)[${categoryIndex++}]")
                wait.until(ExpectedConditions.presenceOfElementLocated(categoryLocator))
                val category = driver.findElement(categoryLocator)

                if (category.text.contains(targetCategory)) {
                    logger.info { "카테고리 번호: $categoryIndex, ${category.text} 으로 작성" }
                    category.clickWhenLoaded()
                    break
                }
            } catch (e: NotFoundException) {
                logger.error(e) { "$targetCategory 포함하는 카테고리 없음: 기본 카테고리로 대체" }
                val noCategorySelector = By.xpath("(//div[@id='category-list']//span)[1]")
                noCategorySelector.clickWhenLoaded()
                break
            }
        }
    }

    @ThrowWithMessage("HTML 선택 에러", TistoryException::class)
    fun setHTML() {
        buttonEditorModeLayerOpen.clickWhenLoaded()

        val htmlSelector = By.xpath("//div[@id='editor-mode-html']")
        htmlSelector.clickWhenLoaded()

        handlePopup(false)
    }

    @ThrowWithMessage("제목 설정 에러", TistoryException::class)
    fun setTitle(title: String) {
        textareaPostTitleInp.sendKeysWhenLoaded(title)
    }

    @ThrowWithMessage("내용 설정 에러", TistoryException::class)
    fun setContent(content: String) {
        preCodeLine.clickWhenLoaded()

        val codeTextAreaLocator = By.xpath("//div[contains(@class, 'CodeMirror')]//textarea")
        codeTextAreaLocator.sendKeysWhenLoaded(content)
    }

    @ThrowWithMessage("태그 설정 에러", TistoryException::class)
    fun setTags(tags: List<String>) {
        tags.forEach { tag ->
            inputTagText.sendKeysWhenLoaded(tag)
        }
    }

    @ThrowWithMessage("제출 에러", TistoryException::class)
    fun submit() {
        buttonPublishLayer.clickWhenLoaded()

        val publishButtonLocator = By.xpath("//*[@id='publish-btn']")
        publishButtonLocator.clickWhenLoaded()
    }
}
