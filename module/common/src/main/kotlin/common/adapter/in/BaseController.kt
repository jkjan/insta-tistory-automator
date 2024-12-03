package common.adapter.`in`

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class BaseController {
    fun <T> responseWithList(list: List<T>): ResponseEntity<List<T>> = ResponseEntity
        .status(
            if (list.isNotEmpty()) {
                HttpStatus.OK
            } else {
                HttpStatus.BAD_REQUEST
            },
        ).body(list)
}
