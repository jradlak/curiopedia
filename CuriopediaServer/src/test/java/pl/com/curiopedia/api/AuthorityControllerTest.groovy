package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pl.com.curiopedia.domain.user.dto.AuthorityDTO
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.service.AuthorityService
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by jakub on 22.06.17.
 */
@WebMvcTest(AuthorityController)
class AuthorityControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {
        @Bean
        AuthorityService authorityService(DetachedMockFactory f) {
            return f.Mock(AuthorityService)
        }
    }

    @Autowired
    AuthorityService authorityService;

    def "can list authorities when signed in"() {
        given:
        signIn()
        authorityService.findAllAuthorities() >> {
            List<AuthorityDTO> content = new ArrayList<>()
            content.add(new AuthorityDTO(Authority.ROLE_AUTHOR))
            content.add(new AuthorityDTO(Authority.ROLE_ADMIN))
            return content
        }

        when:
        def response = perform(get("/api/users/authorities"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$').exists())
            andExpect(jsonPath('$', hasSize(2)))
            andExpect(jsonPath('$[0].username', is(Authority.ROLE_AUTHOR)))
            andExpect(jsonPath('$[1].username', is(Authority.ROLE_ADMIN)))
        }
    }
}
