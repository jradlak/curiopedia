package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import pl.com.curiopedia.domain.curio.dto.SourceDTO
import pl.com.curiopedia.domain.curio.service.SourceService
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by jakub on 02.07.17.
 */
@WebMvcTest(SourceController)
class SourceControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {
        @Bean
        SourceService sourceService(DetachedMockFactory f) {
            return f.Mock(SourceService)
        }
    }

    @Autowired
    SourceService sourceService

    def "can create source"() {
        given:
        signIn()
        LocalDate now = LocalDate.now()
        SourceDTO sourceDTO = SourceDTO.builder()
            .name("source1")
            .description("desc1")
            .date(now.toString())
            .build()

        when:
        String strdto = toJson(sourceDTO)
        def response = perform(post("/api/sources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strdto)
        )

        then:
        with(response) {
            andExpect(status().isOk())
        }
    }

    def "can list all sources"() {
        given:
        signIn()
        sourceService.findAll(_ as PageRequest) >> {
            List<SourceDTO> content = (0..1).collect {
                SourceDTO.builder()
                    .name("source${it}")
                    .description("desc${it}")
                    .date(LocalDate.now().plusMonths(it + 1).toString())
                    .build()
            }

            return new PageImpl<>(content)
        }

        when:
        def response = perform(get("/api/sources"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$.content').exists())
            andExpect(jsonPath('$.content', hasSize(2)))
            andExpect(jsonPath('$.content[0].name', is("source0")))
            andExpect(jsonPath('$.content[0].description', is("desc0")))
            andExpect(jsonPath('$.content[0].date',
                    is(LocalDate.now().plusMonths(1).toString())))
            andExpect(jsonPath('$.content[1].name', is("source1")))
            andExpect(jsonPath('$.content[1].description', is("desc1")))
            andExpect(jsonPath('$.content[1].date',
                    is(LocalDate.now().plusMonths(2).toString())))
        }
    }
}