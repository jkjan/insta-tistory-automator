package com.jun.instatistoryautomatorserver.global.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ThrowWithMessage(
    val message: String,
)
