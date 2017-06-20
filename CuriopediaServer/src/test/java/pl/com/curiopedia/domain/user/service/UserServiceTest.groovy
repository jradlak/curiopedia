package pl.com.curiopedia.domain.user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import pl.com.curiopedia.domain.user.dto.UserParams
import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.repository.AuthorityRepository
import pl.com.curiopedia.domain.user.repository.UserRepository

/**
 * Created by jakub on 19.06.17.
 */
@SuppressWarnings("GroovyPointlessBoolean")
class UserServiceTest extends BaseServiceTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository

    UserService userService

    def setup() {
        userService = new UserServiceImpl(authorityRepository, userRepository, securityContextService)
    }

    def "loadUserByUsername"() {
        given:
        User user = userRepository.save(new User(username: "akira@test.com", password: "secret", name: "akira"))

        when:
        UserDetails userDetails = userService.loadUserByUsername("akira@test.com")

        then:
        user.username == userDetails.username

        when:
        userService.loadUserByUsername("test1@test.com")

        then:
        thrown(UsernameNotFoundException)
    }

    def "can create a user"() {
        given:
        UserParams params = new UserParams("test1@test.com", "secret", "test1", "ROLE_USER")

        when:
        userService.create(params)
        Optional<User> usrDb = userRepository.findOneByUsername("test1@test.com")

        then:
        userRepository.count() == 1
        usrDb.isPresent()
        usrDb.get().username == "test1@test.com"
    }
}
