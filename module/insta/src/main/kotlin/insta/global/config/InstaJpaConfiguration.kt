package insta.global.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["insta.adapter.out.db"])
@EntityScan(basePackages = ["insta.domain"])
class InstaJpaConfiguration
