package com.jun.instatistoryautomatorserver.global.aspect

import com.jun.instatistoryautomatorserver.application.model.InstaService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import com.jun.instatistoryautomatorserver.global.annotation.ThrowWithMessage
import com.jun.instatistoryautomatorserver.global.exception.InstaException
import com.jun.instatistoryautomatorserver.global.exception.TistoryException
import org.springframework.stereotype.Component

@Aspect
@Component
class ExceptionAspect {
    @Around("execution(* com.jun.instatistoryautomatorserver.application.model.TistoryService.*(..)), @annotation(throwWithMessage)")
    fun handleTistoryException(pjp: ProceedingJoinPoint, throwWithMessage: ThrowWithMessage) {
        try {
            pjp.proceed()
        } catch (e: Exception) {
            when (pjp.target.javaClass) {
                InstaService::class.java ->
                    throw InstaException(e.message, e)
                TistoryException::class.java ->
                    throw TistoryException(e.message, e)
            }
        }
    }
}