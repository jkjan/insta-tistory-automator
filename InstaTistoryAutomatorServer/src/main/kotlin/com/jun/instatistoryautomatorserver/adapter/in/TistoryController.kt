package com.jun.instatistoryautomatorserver.adapter.`in`

import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.application.model.TistoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tistory")
class TistoryController(private val tistoryService: TistoryService) : BaseController() {
    @GetMapping
    fun fetchTistoryPost(): ResponseEntity<List<TistoryRequestDTO>> =
        responseWithList(tistoryService.fetchTistoryPosts())

    @GetMapping("/upload")
    fun uploadTistoryPosts(): ResponseEntity<List<String>> =
        responseWithList(tistoryService.uploadTistoryPosts())
}
