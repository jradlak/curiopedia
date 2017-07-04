package pl.com.curiopedia.domain.user.repository

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by jakub on 19.06.17.
 */
@Configuration
@EnableJpaRepositories(basePackages = "pl.com.curiopedia.domain.user")
@EntityScan("pl.com.curiopedia.domain.user.entity")
@EnableAutoConfiguration(exclude = [FlywayAutoConfiguration])
class RepositoryTestConfig {
}
