package pl.com.curiopedia.domain.curio.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.com.curiopedia.domain.curio.dto.CategoryDTO
import pl.com.curiopedia.domain.curio.entity.Category
import pl.com.curiopedia.domain.curio.repository.CategoryRepository
import pl.com.curiopedia.domain.user.service.BaseServiceTest

/**
 * Created by jakub on 29.06.17.
 */
class CategoryServiceTest extends BaseServiceTest {

    @Autowired
    CategoryRepository categoryRepository

    CategoryService categoryService

    def setup() {
        categoryService = new CategoryServiceImpl(categoryRepository)
    }

    def "can create category"() {
        given:
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("category1").description("description1")
                .build()

        when:
        Category category = categoryService.createCategory(categoryDTO)
        Optional<Category> categoryDb = categoryRepository.findOneByName("category1")

        then:
        categoryRepository.count() == 1
        categoryDb.isPresent()
        categoryDb.get().name == "category1"
        categoryDb.get().description == "description1"
    }

    def "can list all categories"() {
        given:
        categoryRepository.save(new Category("category1", "description1"))
        categoryRepository.save(new Category("category2", "description2"))

        when:
        Page<CategoryDTO> categories = categoryService.findAll(new PageRequest(0, 2))

        then:
        categories.size == 2
        categories.content.first().name == "category1"
        categories.content.last().name == "category2"
    }
}
