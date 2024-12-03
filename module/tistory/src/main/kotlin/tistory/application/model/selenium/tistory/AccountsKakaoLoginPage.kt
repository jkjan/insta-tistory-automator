package tistory.application.model.selenium.tistory

import tistory.application.model.selenium.BaseSeleniumPage
import common.global.annotation.AfterSetUp
import common.global.annotation.ThrowWithMessage
import tistory.global.exception.TistoryUploadException
import tistory.global.property.TistoryProperty
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(TistoryProperty::class)
class AccountsKakaoLoginPage(
    private val tistoryProperty: TistoryProperty,
    private val tistoryAuthLoginPage: TistoryAuthLoginPage,
) : BaseSeleniumPage() {
    @FindBy(xpath = "//*[@id='loginId--1']")
    lateinit var inputLogin: WebElement

    @FindBy(xpath = "//*[@id='password--2']")
    lateinit var inputPassword: WebElement

    @FindBy(xpath = "//button[@type='submit']")
    lateinit var buttonHighlightSubmit: WebElement

    override fun setUp() {
        tistoryAuthLoginPage.goToLoginPage()
        super.setUp()
    }

    @AfterSetUp
    @ThrowWithMessage("로그인 에러", TistoryUploadException::class)
    fun login() {
        inputLogin.sendKeysWhenLoaded(tistoryProperty.email)
        inputPassword.sendKeys(tistoryProperty.password)
        buttonHighlightSubmit.clickWhenLoaded()
    }
}
