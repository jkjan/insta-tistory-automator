package tistory.global.exception

class ChromeDriverNotFoundException(override val message: String, override val cause: Throwable? = null) : RuntimeException()