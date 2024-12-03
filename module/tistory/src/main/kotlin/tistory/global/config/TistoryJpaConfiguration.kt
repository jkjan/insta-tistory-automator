package tistory.global.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["tistory.adapter.out.db"])
@EntityScan(basePackages = ["tistory.domain"])
class TistoryJpaConfiguration
