package pl.com.curiopedia.domain.user.service

import org.springframework.beans.factory.annotation.Autowired
import pl.com.curiopedia.domain.user.dto.AuthorityDTO
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.repository.AuthorityRepository

/**
 * Created by jakub on 22.06.17.
 */
class AuthorityServiceTest extends BaseServiceTest {

    @Autowired
    AuthorityRepository authorityRepository;

    AuthorityService authorityService;

    def setup() {
        authorityService = new AuthorityServiceImpl(authorityRepository);
    }

    def "can find all authorities"() {
        given:
        authorityRepository.save(new Authority("ROLE_TEST1"))
        authorityRepository.save(new Authority("ROLE_TEST2"))

        when:
        List<AuthorityDTO> authorities = authorityService.findAllAuthorities()

        then:
        authorities.size() == 5
        authorities.first().getName() == "ROLE_ADMIN"
    }
}