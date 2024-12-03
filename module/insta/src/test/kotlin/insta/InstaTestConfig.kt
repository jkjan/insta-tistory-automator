package insta

import insta.adapter.out.api.InstaApi
import insta.adapter.out.db.InstaRepository
import insta.application.model.InstaService
import insta.global.property.InstaProperty
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import
import org.springframework.web.client.RestTemplate

@TestConfiguration
@EnableAspectJAutoProxy
@Import(InstaService::class, InstaApi::class, RestTemplate::class)
class InstaTestConfig {
    @MockBean
    private lateinit var instaRepository: InstaRepository

    @MockBean
    private lateinit var instaProperty: InstaProperty
}
