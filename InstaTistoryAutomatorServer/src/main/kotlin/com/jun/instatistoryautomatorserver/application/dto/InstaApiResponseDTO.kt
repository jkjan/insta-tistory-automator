package com.jun.instatistoryautomatorserver.application.dto

data class InstaApiResponseDTO (
    var `data`: List<InstaPostResponseDTO>,
    var paging: Paging?,
) {
    data class Paging(
        var cursors: Any?,
        var previous: String?,
        var next: String?,
    )
}
