package com.jun.instatistoryautomatorserver.adapter.`in`

import com.jun.instatistoryautomatorserver.application.dto.InstaPostResponseDTO
import com.jun.instatistoryautomatorserver.application.model.InstaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/insta")
class InstaController(private val instaService: InstaService) : BaseController() {
    @GetMapping
    suspend fun fetchInstaPosts(): ResponseEntity<List<InstaPostResponseDTO>> =
        responseWithList(instaService.fetchInstaPosts())
}
