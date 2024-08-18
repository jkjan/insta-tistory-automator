package com.jun.instatistoryautomatorserver.exception

class TistoryException(
    override val message: String,
    override val cause: Throwable? = null
    ): RuntimeException()