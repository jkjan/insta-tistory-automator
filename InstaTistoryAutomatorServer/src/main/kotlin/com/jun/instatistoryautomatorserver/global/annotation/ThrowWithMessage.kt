package com.jun.instatistoryautomatorserver.global.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ThrowWithMessage(
    val message: String,
    val throwable: KClass<out Throwable>,
)
