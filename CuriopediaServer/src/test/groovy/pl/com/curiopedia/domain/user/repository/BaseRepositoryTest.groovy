package pl.com.curiopedia.domain.user.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import pl.com.curiopedia.config.QueryDSLConfig
import spock.lang.Specification

/**
 * Created by jakub on 19.06.17.
 */
@ActiveProfiles("test")
@Import(value = [QueryDSLConfig])
@DataJpaTest
abstract class BaseRepositoryTest extends Specification {
}
