package com.jun.instatistoryautomatorserver.exception

class InstaException(
    override val message: String,
    override val cause: Throwable? = null
    ): RuntimeException()