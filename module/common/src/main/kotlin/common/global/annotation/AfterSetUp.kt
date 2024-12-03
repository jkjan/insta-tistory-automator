package common.global.annotation

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

/**
 * If this is annotated to a method, an aspect will catch it and call 'setUp' method within a class that is annotated to.
 * You can pass the name as a string value. If the method doesn't exist as a member method in the class it won't be invoked.
 * You will need SetUpAspect with this annotation to make it work properly.
**/
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
annotation class AfterSetUp(
    val value: String = "setUp",
)
