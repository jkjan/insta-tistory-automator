package com.jun.instatistoryautomatorserver.application.dto

import com.jun.instatistoryautomatorserver.domain.TistoryPost

data class TistoryRequestDTO(
    val title: String,
    val category: String = "술",
    val content: String,
    val tags: List<String> = listOf(),
) {
    constructor(tistoryPost: TistoryPost) : this(
        title = tistoryPost.title!!,
        category = tistoryPost.category!!,
        content = tistoryPost.content!!,
        tags = tistoryPost.tags!!.split("|"),
    )
}
