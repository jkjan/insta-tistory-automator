package com.jun.instatistoryautomatorserver.application.model.selenium.tistory

import com.jun.instatistoryautomatorserver.application.model.selenium.BaseSeleniumPage
import com.jun.instatistoryautomatorserver.global.annotation.AfterSetUp
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
        wait.until(ExpectedConditions.elementToBeClickable(ulTestTitleNowhereMan))
        return ulTestTitleNowhereMan.getAttribute("href")
    }
}
