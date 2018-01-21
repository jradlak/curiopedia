package pl.com.curiopedia.domain.curio.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.com.curiopedia.domain.curio.dto.CategoryDTO
import pl.com.curiopedia.domain.curio.entity.Category
import pl.com.curiopedia.domain.curio.exceptions.CategoryNameAlreadyExists
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
        CategoryDTO categoryDTO = makeCategory("category1")

        when:
        categoryService.createCategory(categoryDTO)
        Optional<Category> categoryDb = categoryRepository.findOneByName("category1")

        then:
        categoryRepository.count() == 1
        categoryDb.isPresent()
        categoryDb.get().name == "category1"
        categoryDb.get().description == "description1"
    }

    def "can update existing category"() {
        given:
        CategoryDTO categoryDTO = makeCategory("category1")
        categoryService.createCategory(categoryDTO)
        CategoryDTO categoryFromDb = categoryService.findOneByName(categoryDTO.getName()).get()

        when:
        CategoryDTO modCategory = CategoryDTO.builder()
            .id(categoryFromDb.getId())
            .name(categoryFromDb.getName() + "MOD")
            .description(categoryFromDb.getDescription() + "MOD")
            .build()
        Category categoryModded = categoryService.updateCategory(modCategory)

        then:
        categoryModded.getId() == categoryFromDb.getId()
        categoryModded.getName().equals(categoryFromDb.getName() + "MOD")
        categoryModded.getDescription().equals(categoryFromDb.getDescription() + "MOD")
    }

    def "can not update category when name exists"() {
        given:
        CategoryDTO categoryDTO = makeCategory("name1")
        CategoryDTO categoryDTO2 = makeCategory("name2")
        categoryService.createCategory(categoryDTO)
        categoryService.createCategory(categoryDTO2)

        CategoryDTO categoryFromDb = categoryService.findOneByName(categoryDTO.getName()).get()

        when:
        CategoryDTO modCategory = CategoryDTO.builder()
                .id(categoryFromDb.getId())
                .name("name2")
                .description(categoryFromDb.getDescription() + "MOD")
                .build()

        categoryService.updateCategory(modCategory)

        then:
        thrown CategoryNameAlreadyExists
    }

    def "can delete existing category"() {
        given:
        CategoryDTO categoryDTO = makeCategory("category1")
        categoryService.createCategory(categoryDTO)
        CategoryDTO categoryFromDb = categoryService.findOneByName(categoryDTO.getName()).get()
        long categoriesCount = categoryRepository.count()

        when:
        categoryService.deleteCategory(categoryFromDb)

        then:
        categoriesCount == categoryRepository.count() + 1
    }

    def "can find category by id"() {
        given:
        CategoryDTO categoryDTO = makeCategory("category1")
        Category category = categoryService.createCategory(categoryDTO)

        when:
        Optional<Category> categoryDb = categoryService.findOne(category.getId())

        then:
        categoryDb.isPresent()
        categoryDb.get().getName().equals("category1")
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

    private CategoryDTO makeCategory(String name) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(name).description("description1")
                .build()

        return categoryDTO
    }
}
