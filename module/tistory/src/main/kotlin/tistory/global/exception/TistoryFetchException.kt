package tistory.global.exception

class TistoryFetchException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException()
