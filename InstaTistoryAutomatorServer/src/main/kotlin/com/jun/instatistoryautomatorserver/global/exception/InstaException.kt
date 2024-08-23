package com.jun.instatistoryautomatorserver.global.exception

class InstaException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException()
