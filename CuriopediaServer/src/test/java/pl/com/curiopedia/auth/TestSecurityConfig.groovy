package pl.com.curiopedia.auth

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import pl.com.curiopedia.domain.user.repository.UserRepository
import spock.mock.DetachedMockFactory

/**
 * Created by jakub on 19.06.17.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
@TestConfiguration
class TestSecurityConfig {

    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    UserDetailsService userDetailsService() {
        factory.Stub(UserDetailsService)
    }

    @Bean
    TokenAuthenticationService tokenAuthenticationService() {
        factory.Stub(TokenAuthenticationService)
    }

    @Bean
    UserRepository userRepository() {
        factory.Stub(UserRepository)
    }
}
