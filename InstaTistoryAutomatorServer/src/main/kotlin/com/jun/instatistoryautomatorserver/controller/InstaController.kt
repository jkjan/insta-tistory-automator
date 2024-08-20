package com.jun.instatistoryautomatorserver.controller

import com.jun.instatistoryautomatorserver.entity.InstaPost
import com.jun.instatistoryautomatorserver.service.InstaService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/insta")
class InstaController(private val instaService: InstaService) {
    @GetMapping
    suspend fun fetchInstaPosts(): ResponseEntity<List<InstaPost>> {
        val instaPosts = instaService.fetchInstaPost()

        return ResponseEntity
            .status(
                if (instaPosts.isNotEmpty())
                    HttpStatus.OK
                else
                    HttpStatus.NO_CONTENT)
            .body(instaPosts)
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}