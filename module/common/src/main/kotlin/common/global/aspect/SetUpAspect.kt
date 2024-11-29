package common.global.aspect

import common.global.annotation.AfterSetUp
import io.github.oshai.kotlinlogging.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class SetUpAspect {
    @Before("@annotation(afterSetUp)")
    @Suppress("TooGenericExceptionCaught")
    fun setUp(joinPoint: JoinPoint, afterSetUp: AfterSetUp) {
        try {
            logger.info { "initiated setup: ${joinPoint.target.javaClass.simpleName}" }
            joinPoint.target.javaClass.getMethod(afterSetUp.value).invoke(joinPoint.target)
        } catch (e: NoSuchMethodException) {
            logger.warn(e) { "Can't invoke ${afterSetUp.value}. No such method in a class ${joinPoint.target.javaClass.simpleName}" }
        } catch (e: Exception) {
            logger.error(e) { "Error occurred during ${afterSetUp.value}." }
        }
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
