package pl.com.curiopedia.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.Authentication
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions
import pl.com.curiopedia.auth.TokenAuthenticationService
import pl.com.curiopedia.auth.UserAuthentication
import pl.com.curiopedia.domain.user.entity.User
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import javax.servlet.http.HttpServletRequest

/**
 * Created by jakub on 19.06.17.
 */
@ActiveProfiles("test")
@ComponentScan(basePackages = ["pl.com.curiopedia.auth"])
abstract class BaseControllerTest extends Specification {

    @SuppressWarnings("GroovyUnusedDeclaration")
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestConfiguration
    static class Config {
        @Bean
        DetachedMockFactory detachedMockFactory() {
            return new DetachedMockFactory()
        }
    }

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService

    ResultActions perform(RequestBuilder requestBuilder) {
        return mockMvc.perform(requestBuilder)
    }

    User signIn(User user) {
        Authentication auth = new UserAuthentication(user)
        tokenAuthenticationService.getAuthentication(_ as HttpServletRequest) >> auth
        return user
    }

    User signIn() {
        User user = new User(id: 1, username: "test@test.com", password: "secret123", name: "test")
        Authentication auth = new UserAuthentication(user)
        tokenAuthenticationService.getAuthentication(_ as HttpServletRequest) >> auth
        return user
    }
}