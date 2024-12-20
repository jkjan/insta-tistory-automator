package tistory.global.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "webdriver")
data class WebDriverProperty(
    val realChromeEnabled: Boolean = false,
    val timeout: Long = 3,
    val arguments: List<String> = listOf(),
    val chromeDriverPath: String = "",
    val remoteUrl: String,
)
