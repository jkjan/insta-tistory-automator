package tistory.global.exception

class TistoryUploadException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException()
