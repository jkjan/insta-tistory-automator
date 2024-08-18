package com.jun.instatistoryautomatorserver.exception

class PopupException(
    override val message: String,
    override val cause: Throwable? = null
    ): RuntimeException()