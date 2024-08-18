package com.jun.instatistoryautomatorserver.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ThrowWithMessage(
    val message: String,
)
