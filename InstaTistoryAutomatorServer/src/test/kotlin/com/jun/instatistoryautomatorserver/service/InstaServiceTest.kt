package com.jun.instatistoryautomatorserver.service

import com.jun.instatistoryautomatorserver.repository.InstaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class InstaServiceTest {
    @Autowired
    private lateinit var instaService: InstaService

    @Autowired
    private lateinit var instaRepository: InstaRepository

    @Test
    @ExperimentalCoroutinesApi
    fun fetchInstaPost() = runTest {
        launch {
            instaService.fetchInstaPost()
            delay(10000)
        }

        val instaPosts = instaRepository.findAll()
        assertThat(instaPosts).isNotEmpty
    }
}