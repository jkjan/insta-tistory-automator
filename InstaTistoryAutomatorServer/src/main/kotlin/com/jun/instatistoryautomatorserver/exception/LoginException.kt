package com.jun.instatistoryautomatorserver.exception

class LoginException(
    override val message: String,
    override val cause: Throwable? = null
    ): RuntimeException() {
}