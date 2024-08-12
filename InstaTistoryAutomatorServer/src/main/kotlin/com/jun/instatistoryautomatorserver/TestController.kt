package com.jun.instatistoryautomatorserver

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(private val testRepository: TestRepository) {
    @GetMapping("/{id}")
    fun hello(@PathVariable id: Long): String =
        with(testRepository.findById(id).get()) {
            "$test1:$test2"
        }
}