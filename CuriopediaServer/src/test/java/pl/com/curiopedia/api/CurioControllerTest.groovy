package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import pl.com.curiopedia.domain.curio.dto.CategoryDTO
import pl.com.curiopedia.domain.curio.dto.CurioDTO
import pl.com.curiopedia.domain.curio.dto.SourceDTO
import pl.com.curiopedia.domain.curio.dto.TagsDTO
import pl.com.curiopedia.domain.curio.entity.Category
import pl.com.curiopedia.domain.curio.entity.Source
import pl.com.curiopedia.domain.curio.entity.Tag
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException
import pl.com.curiopedia.domain.curio.repository.CategoryRepository
import pl.com.curiopedia.domain.curio.repository.SourceRepository
import pl.com.curiopedia.domain.curio.repository.TagRepository
import pl.com.curiopedia.domain.curio.service.CurioService
import pl.com.curiopedia.domain.user.dto.UserDTO
import pl.com.curiopedia.domain.user.entity.Authority
import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.repository.UserRepository
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by jakub on 28.06.17.
 */
@WebMvcTest(CurioController)
class CurioControllerTest extends BaseControllerTest{
    @TestConfiguration
    static class Config {
        @Bean
        CurioService curioService(DetachedMockFactory f) {
            return f.Mock(CurioService)
        }

        @Bean
        CategoryRepository categoryRepository(DetachedMockFactory f) {
            return f.Mock(CategoryRepository)
        }

        @Bean
        SourceRepository sourceRepository(DetachedMockFactory f) {
            return f.Mock(SourceRepository)
        }

        @Bean
        TagRepository Repository(DetachedMockFactory f) {
            return f.Mock(TagRepository)
        }
    }

    @Autowired
    CurioService curioService;

    @Autowired
    CategoryRepository categoryRepository

    @Autowired
    SourceRepository sourceRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    TagRepository tagRepository

    def "can create curio"() {
        given:
        signIn()
        userRepository.save(new User(username: "test123@test.com", password: "secret", name: "akira123"))
        sourceRepository.save(new Source("source1", "sourceDesc1", LocalDate.now()))
        categoryRepository.save(new Category("category1", "categoryDesc1"))
        tagRepository.save(new Tag("tag1"))

        CurioDTO curioDTO = CurioDTO.builder()
                .author("test123@test.com")
                .category("category1")
                .source("source1")
                .tags(TagsDTO.builder().tags(new HashSet<String>(Arrays.asList("Tag1"))).build())
                .title("curio1")
                .content("content1")
                .description("description1")
                .build()

        when:
        String curioJson = toJson(curioDTO)
        def response = perform(post("/api/curios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(curioJson)
        )

        then:
        with(response) {
            andExpect(status().isOk())
            //andExpect(jsonPath('$.code', is("email_already_taken")))
        }
    }
}
