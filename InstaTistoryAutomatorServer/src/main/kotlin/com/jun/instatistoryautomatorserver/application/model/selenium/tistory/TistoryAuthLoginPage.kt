package com.jun.instatistoryautomatorserver.application.model.selenium.tistory

import com.jun.instatistoryautomatorserver.application.model.selenium.BaseSeleniumPage
import com.jun.instatistoryautomatorserver.global.annotation.AfterSetUp
import com.jun.instatistoryautomatorserver.global.property.TistoryProperty
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component

// page_url = https://www.tistory.com/auth/login?redirectUrl=https%3A%2F%2Fnowhereland.tistory.com%2Fmanage%2Fnewpost%2F
@Component
class TistoryAuthLoginPage(private val tistoryProperty: TistoryProperty) : BaseSeleniumPage() {
    @FindBy(xpath = "//a[contains(@class, 'btn_login')]")
    lateinit var linkLogin: WebElement

    override fun setUp() {
        driver.get(tistoryProperty.entryUrl)
        super.setUp()
    }

    @AfterSetUp
    fun goToLoginPage() {
        linkLogin.clickWhenLoaded()
    }
}
