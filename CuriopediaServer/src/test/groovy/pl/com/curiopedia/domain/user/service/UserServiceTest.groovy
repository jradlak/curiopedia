package pl.com.curiopedia.domain.user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import pl.com.curiopedia.domain.user.dto.UserDTO
import pl.com.curiopedia.domain.user.dto.UserParams
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.repository.AuthorityRepository
import pl.com.curiopedia.domain.user.repository.UserRepository

/**
 * Created by jakub on 19.06.17.
 */
@SuppressWarnings("GroovyPointlessBoolean")
class UserServiceTest extends BaseServiceTest {

    @Autowired
    AuthorityRepository authorityRepository

    @Autowired
    UserRepository userRepository

    UserService userService

    def setup() {
        userService = new UserServiceImpl(authorityRepository, userRepository, securityContextService)
    }

    def "can find a user when not signed in"() {
        given:
        User user = userRepository.save(new User(username: "akira@test.com", password: "secret", name: "akira"))

        when:
        UserDTO userDTO = userService.findOne(user.id).get()

        then:
        userDTO.id == user.id
    }

    def "can create a user"() {
        given:
        UserParams params = new UserParams("test1@test.com", "secret", "test1", Authority.ROLE_AUTHOR)

        when:
        User user = userService.create(params)
        Optional<User> usrDb = userRepository.findOneByUsername("test1@test.com")

        then:
        userRepository.count() == 1
        usrDb.isPresent()
        usrDb.get().username == "test1@test.com"
        usrDb.get().authorities.contains(Authority.guestAuthority())
        usrDb.get().authorities.contains(Authority.authorAuthority())
    }

    def "can delete user"() {
        given:
        User user = userRepository.save(new User(username: "akira@test.com", password: "secret", name: "akira"))
        User currentUser = userRepository.save(new User(username: "current@test.com", password: "secret", name: "akira"))
        signIn(currentUser)

        when:
        userService.delete(new UserParams("akira@test.com", "secret", "akira@test.com", Authority.ROLE_AUTHOR))

        then:
        userRepository.count() == 1
    }

    def "can find paged user list"() {
        given:
        //noinspection GroovyUnusedAssignment
        User user1 = userRepository.save(new User(username: "test1@test.com", password: "secret", name: "akira"))
        User user2 = userRepository.save(new User(username: "test2@test.com", password: "secret", name: "akira"))

        when:
        PageRequest pageRequest = new PageRequest(1, 1)
        Page<UserDTO> page = userService.findAll(pageRequest)

        then:
        page.content.first().id == user2.id
        page.totalElements == 2
    }

    def "can update a user"() {
        given:
        User user = userRepository.save(new User(username: "test2@test.com", password: "secret", name: "akira"))
        UserParams params = new UserParams("test2@test.com", "secret2", "test2", Authority.ROLE_AUTHOR)

        when:
        userService.update(params)

        then:
        user.username == "test2@test.com"
        user.name == "test2"

        when:
        params = new UserParams("test2@test.com", "secret3", "test3", Authority.ROLE_ADMIN)
        userService.update(params)
        Optional<User> usrDb = userRepository.findOneByUsername("test2@test.com")

        then:
        usrDb.get().username == "test2@test.com"
        usrDb.get().name == "test3"
        usrDb.get().authorities.contains(Authority.adminAuthority())
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
}
