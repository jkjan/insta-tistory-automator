package insta.adapter.`in`

import insta.application.dto.InstaPostResponseDTO
import insta.application.model.InstaService
import common.adapter.`in`.BaseController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/insta")
class InstaController(private val instaService: InstaService) : BaseController() {
    @GetMapping
    fun fetchInstaPosts(): ResponseEntity<List<InstaPostResponseDTO>> =
        responseWithList(instaService.fetchInstaPosts())
}
