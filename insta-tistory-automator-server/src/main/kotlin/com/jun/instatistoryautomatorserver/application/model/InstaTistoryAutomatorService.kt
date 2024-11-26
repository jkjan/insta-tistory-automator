package com.jun.instatistoryautomatorserver.application.model

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
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
