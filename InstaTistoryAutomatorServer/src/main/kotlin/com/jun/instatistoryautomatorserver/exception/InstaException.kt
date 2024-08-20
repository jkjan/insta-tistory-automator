package com.jun.instatistoryautomatorserver.exception

class InstaException(
    override val message: String? = null,
    override val cause: Throwable? = null
    ): RuntimeException()