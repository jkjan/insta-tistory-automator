package tistory.application.model.selenium.tistory

import tistory.application.model.selenium.BaseSeleniumPage
import common.global.annotation.AfterSetUp
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import org.springframework.stereotype.Component

// page_url = https://nowhereland.tistory.com/manage/posts
@Component
class TistoryPostListPage : BaseSeleniumPage() {
    @FindBy(xpath = "(//ul[contains(@class, 'list_post')]//a)[1]")
    lateinit var ulTestTitleNowhereMan: WebElement

    @AfterSetUp
    fun getFirstPostUrl(): String {
        webDriverManager.getWait().until(ExpectedConditions.elementToBeClickable(ulTestTitleNowhereMan))
        return ulTestTitleNowhereMan.getAttribute("href")
    }
}
