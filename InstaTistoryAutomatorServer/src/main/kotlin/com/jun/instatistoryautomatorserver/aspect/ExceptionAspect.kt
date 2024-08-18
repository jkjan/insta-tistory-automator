package com.jun.instatistoryautomatorserver.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import com.jun.instatistoryautomatorserver.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.exception.TistoryException
import org.springframework.stereotype.Component

@Aspect
@Component
class ExceptionAspect {
    @Around("execution(* com.jun.instatistoryautomatorserver.component.TistoryUploader.*(..)), @annotation(throwWithMessage)")
    fun handleTistoryException(pjp: ProceedingJoinPoint, throwWithMessage: ThrowWithMessage) {
        try {
            pjp.proceed()
        } catch (e: Exception) {
            throw TistoryException(throwWithMessage.message, e)
        }
    }
}