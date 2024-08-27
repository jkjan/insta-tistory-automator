package com.jun.instatistoryautomatorserver.adapter.`in`

import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.application.model.TistoryService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
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
    suspend fun uploadTistoryPosts(): ResponseEntity<String> {
        coroutineScope {
            launch {
                responseWithList(tistoryService.uploadTistoryPosts())
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("잠깐만요!")
    }
}
