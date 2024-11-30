package com.jun.instatistoryautomatorserver.application.model

import insta.application.model.InstaService
import tistory.application.model.TistoryService
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
@ComponentScan("insta", "tistory")
class InstaTistoryAutomatorService(
    private val instaService: InstaService,
    private val tistoryService: TistoryService,
) {
    @Async
    fun fetchFromInstaAndUploadToTistory() {
        instaService.fetchInstaPosts()
        tistoryService.fetchTistoryPosts()
        tistoryService.uploadTistoryPosts()
    }
}
