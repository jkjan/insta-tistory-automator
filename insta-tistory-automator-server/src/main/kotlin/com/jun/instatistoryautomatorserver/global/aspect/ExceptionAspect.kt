package com.jun.instatistoryautomatorserver.global.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import org.springframework.stereotype.Component

@Aspect
@Component
class ExceptionAspect {
    @Around("@annotation(throwWithMessage)")
    @Suppress("TooGenericExceptionCaught")
    fun handleTistoryException(pjp: ProceedingJoinPoint, throwWithMessage: ThrowWithMessage): Any? {
        try {
            return pjp.proceed()
        } catch (e: Exception) {
            throw throwWithMessage.throwable.constructors.first().call(throwWithMessage.message, e)
        }
    }
}
