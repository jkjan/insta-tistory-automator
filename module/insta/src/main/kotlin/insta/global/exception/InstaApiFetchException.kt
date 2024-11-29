package insta.global.exception

class InstaApiFetchException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException()
