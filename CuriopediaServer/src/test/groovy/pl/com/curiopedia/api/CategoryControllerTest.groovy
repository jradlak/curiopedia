package pl.com.curiopedia.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import pl.com.curiopedia.domain.curio.dto.CategoryDTO
import pl.com.curiopedia.domain.curio.service.CategoryService

import spock.mock.DetachedMockFactory

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by jakub on 30.06.17.
 */
@WebMvcTest(CategoryController)
class CategoryControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {
        @Bean
        CategoryService categoryService(DetachedMockFactory f) {
            return f.Mock(CategoryService)
        }
    }

    @Autowired
    CategoryService categoryService

    def "can create category"() {
        given:
        signIn()
        CategoryDTO dto = CategoryDTO.builder()
                .name("category1")
                .description("desc1")
                .build()

        when:
        String strdto = toJson(dto)
        def response = perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strdto)
        )

        then:
        with(response) {
            andExpect(status().isOk())
        }
    }

    def "can update category"() {
        given:
        signIn()
        CategoryDTO dto = CategoryDTO.builder()
                .name("category1")
                .description("desc1")
                .build()

        CategoryDTO dtoMOD = CategoryDTO.builder()
                .id(1)
                .name("category1")
                .description("desc2")
                .build()

        String strdto = toJson(dto)
        perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strdto))

        when:
        String strdtoMOD = toJson(dtoMOD)
        def response = perform(patch("/api/categories/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(strdtoMOD))

        then:
        with(response) {
            andExpect(status().isOk())
        }
    }

    def "can list all categories"() {
        given:
        signIn()
        categoryService.findAll(_ as PageRequest) >> {
            List<CategoryDTO> content = (0..1).collect {
                CategoryDTO.builder()
                    .name("category${it}")
                    .description("description${it}")
                    .build()
            }
            return new PageImpl<>(content)
        }

        when:
        def response = perform(get("/api/categories"))

        then:
        with(response) {
            andExpect(status().isOk())
            andExpect(jsonPath('$.content').exists())
            andExpect(jsonPath('$.content', hasSize(2)))
            andExpect(jsonPath('$.content[0].name', is("category0")))
            andExpect(jsonPath('$.content[0].description', is("description0")))
            andExpect(jsonPath('$.content[1].name', is("category1")))
            andExpect(jsonPath('$.content[1].description', is("description1")))
        }
    }
}
