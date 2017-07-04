package pl.com.curiopedia.domain.user.service

import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.repository.BaseRepositoryTest

/**
 * Created by jakub on 19.06.17.
 */
abstract class BaseServiceTest extends BaseRepositoryTest {

    SecurityContextService securityContextService = Mock(SecurityContextService)

    Optional<User> currentUser

    def setup() {
        // default not signed in
        currentUser = Optional.empty()
        securityContextService.currentUser() >> { currentUser }
    }

    def signIn(User user) {
        currentUser = Optional.of(user)
    }

    def cleanup() {
        currentUser = Optional.empty()
    }
}
