package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import pl.com.curiopedia.domain.user.dto.UserDTO
import pl.com.curiopedia.domain.user.dto.UserParams
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.service.UserService
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException
import spock.mock.DetachedMockFactory

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.nullValue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
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

    def "can not signup when email is duplicated"() {
        given:
        def email = "akirasosa@test.com"
        def password = "secret123"
        def name = "akira1"
        userService.create(_ as UserParams) >> {
            throw new DataIntegrityViolationException("")
        }

        when:
        def response = perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(email: email, password: password, name: name))
        )

        then:
        with(response) {
            andExpect(status().isBadRequest())
            andExpect(jsonPath('$.code', is("email_already_taken")))
        }
    }

    def "can list users when signed in"() {
        given:
        signIn()
        userService.findAll(_ as PageRequest) >> {
            List<UserDTO> content = (0..1).collect {
                User u = new User(id: it, username: "test${it}@test.com", password: "secret", name: "test${it}")
                UserDTO.builder()
                        .id(u.id)
                        .username(u.name)
                        .authority(Authority.ROLE_AUTHOR)
                        .build()
            }
            return new PageImpl<>(content)
        }

        when:
        def response = perform(get("/api/users"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$.content').exists())
            andExpect(jsonPath('$.content', hasSize(2)))
            andExpect(jsonPath('$.content[0].username', is("test0")))
            andExpect(jsonPath('$.content[0].email', nullValue()))
            andExpect(jsonPath('$.content[1].username', is("test1")))
        }
    }

    def "can show user"() {
        given:
        userService.findOne(1) >> {
            User user = new User(id: 1, username: "test1@test.com", password: "secret", name: "test")
            UserDTO userDTO = UserDTO.builder()
                    .id(user.id)
                    .username(user.name)
                    .authority(Authority.ROLE_AUTHOR)
                    .build()
            return Optional.of(userDTO)
        }

        when:
        def response = perform(get("/api/users/1"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$.username', is("test")))
            andExpect(jsonPath('$.email', nullValue()))
        }
    }

    def "can not show user when user was not found"() {
        given:
        userService.findOne(1) >> { throw new UserNotFoundException() }

        when:
        def response = perform(get("/api/users/1"))

        then:
        with(response) {
            andExpect(status().isNotFound())
        }
    }

    def "can show logged in user when signed in"() {
        given:
        User user = signIn()
        userService.findMe() >> {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.id)
                    .username(user.name)
                    .email(user.username)
                    .authority(Authority.ROLE_AUTHOR)
                    .build()
            Optional.of(userDTO)
        }

        when:
        def response = perform(get("/api/users/me"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$.username', is("test")))
            andExpect(jsonPath('$.email', is("test@test.com")))
        }
    }
}
