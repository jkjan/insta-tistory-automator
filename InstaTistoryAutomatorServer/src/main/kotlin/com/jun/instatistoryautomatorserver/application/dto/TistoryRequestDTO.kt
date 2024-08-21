package com.jun.instatistoryautomatorserver.application.dto

data class TistoryRequestDTO (
    val title: String,
    val category: String = "술",
    val imageUrl: String,
    val content: String,
    val tags: List<String> = listOf(),
)