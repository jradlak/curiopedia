package pl.com.curiopedia.domain.curio.factory

import org.springframework.beans.factory.annotation.Autowired
import pl.com.curiopedia.domain.curio.dto.TagsDTO
import pl.com.curiopedia.domain.curio.entity.Tag
import pl.com.curiopedia.domain.curio.repository.TagRepository
import pl.com.curiopedia.domain.user.service.BaseServiceTest

/**
 * Created by simple on 24.06.17.
 */
@SuppressWarnings("GroovyPointlessBoolean")
class TagsFactoryTest extends BaseServiceTest {
    @Autowired
    TagRepository tagRepository

    TagsFactory tagsFactory

    def setup() {
        tagsFactory = new TagsFactoryImpl(tagRepository)
    }

    def "can make tags"() {
        given:
        tagRepository.save(new Tag("Tag1"))
        tagRepository.save(new Tag("Tag2"))
        TagsDTO dto = TagsDTO.builder().tags(new HashSet<>(Arrays.asList("Tag2", "Tag3", "Tag4"))).build()

        when:
        Set<Tag> result = tagsFactory.makeSetOfTags(dto)
        Set<Tag> tagsDb = new HashSet<>(tagRepository.findAll())

        then:
        result.size() == 3
        tagsDb.size() == 4
        result.containsAll(Arrays.asList(new Tag("Tag2"),
                new Tag("Tag3"), new Tag("Tag4")))
        tagsDb.containsAll(Arrays.asList(new Tag("Tag2"),
                new Tag("Tag3"), new Tag("Tag4"), new Tag("Tag1")))
    }
}
