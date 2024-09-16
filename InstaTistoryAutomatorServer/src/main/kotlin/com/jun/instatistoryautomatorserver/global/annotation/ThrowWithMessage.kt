package com.jun.instatistoryautomatorserver.global.annotation

import kotlin.reflect.KClass
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Order(Ordered.LOWEST_PRECEDENCE)
annotation class ThrowWithMessage(
    val message: String,
    val throwable: KClass<out Throwable>,
)
