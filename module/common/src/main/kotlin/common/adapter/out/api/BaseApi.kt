package common.adapter.out.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
abstract class BaseApi {
    @Autowired
    lateinit var restTemplate: RestTemplate

    companion object {
        fun <T> ResponseEntity<T>.isOkAndHasBody(): Boolean = this.statusCode.is2xxSuccessful && this.hasBody()
    }
}
