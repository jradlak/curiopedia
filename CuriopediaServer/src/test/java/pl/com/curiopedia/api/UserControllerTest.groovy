package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import pl.com.curiopedia.domain.user.dto.UserParams
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.service.UserService
import spock.mock.DetachedMockFactory

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by jakub on 20.06.17.
 */

@WebMvcTest(UserController)
class UserControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {
        @Bean
        UserService userService(DetachedMockFactory f) {
            return f.Mock(UserService)
        }
    }

    @Autowired
    UserService userService

    def "can signup"() {
        given:
        def email = "akirasosa@test.com"
        def password = "secret123"
        def name = "akira"

        when:
        def response = perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(email: email, password: password, name: name, authority: Authority.ROLE_AUTHOR))
        )

        then:
        1 * userService.create(new UserParams(email, password, name, Authority.ROLE_AUTHOR))
        response.andExpect(status().isOk())
    }
}
