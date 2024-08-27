package com.jun.instatistoryautomatorserver.application.model

import jakarta.transaction.Transactional
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class InstaTistoryAutomatingService(
    private val instaService: InstaService,
    private val tistoryService: TistoryService,
) {
    @Transactional
    suspend fun fetchFromInstaAndUploadToTistory() {
        coroutineScope {
            launch {
                instaService.fetchInstaPosts()
                tistoryService.fetchTistoryPosts()
                tistoryService.uploadTistoryPosts()
            }
        }
    }
}
