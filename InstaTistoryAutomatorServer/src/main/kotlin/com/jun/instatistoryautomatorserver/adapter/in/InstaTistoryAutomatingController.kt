package com.jun.instatistoryautomatorserver.adapter.`in`

import com.jun.instatistoryautomatorserver.application.model.InstaTistoryAutomatingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/automator")
class InstaTistoryAutomatingController(
    private val instaTistoryAutomatingService: InstaTistoryAutomatingService,
) {
    @GetMapping
    suspend fun fetchFromInstaAndUploadToTistory(): ResponseEntity<String> {
        instaTistoryAutomatingService.fetchFromInstaAndUploadToTistory()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("인스타 목록에서 가져와 티스토리에 업로드합니다.")
    }
}
