package com.jun.instatistoryautomatorserver.application.model

import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.application.model.selenium.tistory.NowherelandTistoryManageNPage
import com.jun.instatistoryautomatorserver.global.property.TistoryProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(TistoryProperty::class)
class TistoryService(
    private val nowherelandTistoryManageNPage: NowherelandTistoryManageNPage,
) {
    fun uploadPostToTistoryBlog(tistoryRequestDTO: TistoryRequestDTO) {
        tistoryRequestDTO.let { dto ->
            with(nowherelandTistoryManageNPage) {
                setUp()
                handlePopup()
                setCategory(dto.category)
                setHTML()
                setTitle(dto.title)
                setContent(dto.content)
                setTags(dto.tags)
                submit()
            }
        }
    }
}
