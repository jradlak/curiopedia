package pl.com.curiopedia.domain.curio.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.com.curiopedia.domain.curio.dto.SourceDTO
import pl.com.curiopedia.domain.curio.entity.Source
import pl.com.curiopedia.domain.curio.repository.SourceRepository
import pl.com.curiopedia.domain.user.service.BaseServiceTest

import java.time.LocalDate

/**
 * Created by jakub on 02.07.17.
 */
class SourceServiceTest extends BaseServiceTest{

    @Autowired
    SourceRepository sourceRepository

    SourceService sourceService

    def setup() {
        sourceService = new SourceServiceImpl(sourceRepository)
    }

    def "can create source"() {
        given:
        LocalDate now = LocalDate.now()
        SourceDTO sourceDTO = SourceDTO.builder()
            .name("source1")
            .description("description1")
            .date(now)
            .build()

        when:
        sourceService.createSource(sourceDTO)
        Optional<Source> sourceDb = sourceRepository.findOneByName("source1")

        then:
        sourceRepository.count() == 1
        sourceDb.isPresent()
        sourceDb.get().description == "description1"
        sourceDb.get().name == "source1"
        sourceDb.get().date == now
    }

    def "can list all sources"() {
        given:
        LocalDate now = LocalDate.now()
        sourceRepository.save(new Source("source1", "desc1", now))
        sourceRepository.save(new Source("source2", "desc2", now))

        when:
        Page<SourceDTO> sources = sourceService.findAll(new PageRequest(0, 2))
        SourceDTO first = sources.content.first()
        SourceDTO last = sources.content.last()

        then:
        sources.size() == 2
        first.name == "source1"
        first.description == "desc1"
        last.name == "source2"
        last.description == "desc2"

    }
}
