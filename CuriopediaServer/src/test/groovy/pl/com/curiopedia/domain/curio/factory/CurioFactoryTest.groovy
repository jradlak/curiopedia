package pl.com.curiopedia.domain.curio.factory

import org.springframework.beans.factory.annotation.Autowired
import pl.com.curiopedia.domain.curio.dto.CurioDTO
import pl.com.curiopedia.domain.curio.dto.TagsDTO
import pl.com.curiopedia.domain.curio.entity.Category
import pl.com.curiopedia.domain.curio.entity.Curio
import pl.com.curiopedia.domain.curio.entity.Source
import pl.com.curiopedia.domain.curio.entity.Tag
import pl.com.curiopedia.domain.curio.repository.CategoryRepository
import pl.com.curiopedia.domain.curio.repository.SourceRepository
import pl.com.curiopedia.domain.curio.repository.TagRepository
import pl.com.curiopedia.domain.user.entity.User
import pl.com.curiopedia.domain.user.repository.UserRepository
import pl.com.curiopedia.domain.user.service.BaseServiceTest

import java.time.LocalDate

/**
 * Created by jakub on 26.06.17.
 */
@SuppressWarnings("GroovyPointlessBoolean")
class CurioFactoryTest extends BaseServiceTest {

    @Autowired
    CategoryRepository categoryRepository

    @Autowired
    SourceRepository sourceRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    TagRepository tagRepository

    TagsFactory tagsFactory

    CurioFactory curioFactory;

    def setup() {
        tagsFactory = new TagsFactoryImpl(tagRepository)
        curioFactory = new CurioFactoryImpl(categoryRepository, sourceRepository, userRepository, tagsFactory)
    }

    def "can make curio"() {
        given:
        userRepository.save(new User(username: "test1@test.com", password: "secret", name: "akira"))
        sourceRepository.save(new Source("source1", "sourceDesc1", LocalDate.now()))
        categoryRepository.save(new Category("category1", "categoryDesc1"))
        tagRepository.save(new Tag("tag1"))

        CurioDTO curioDTO = CurioDTO.builder()
                .author("test1@test.com")
                .category("category1")
                .source("source1")
                .tags(TagsDTO.builder().tags(new HashSet<String>(Arrays.asList("Tag1"))).build())
                .title("curio1")
                .content("content1")
                .description("description1")
                .build()

        when:
        Curio curio = curioFactory.makeCurio(curioDTO)

        then:
        curio.title == "curio1"
        curio.author.username == "test1@test.com"
        curio.category.name == "category1"
        curio.source.name == "source1"
        curio.tags.contains(new Tag("Tag1"))
        curio.content == "content1"
        curio.description == "description1"
    }
}
