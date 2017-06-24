package pl.com.curiopedia.domain.curio.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.curiopedia.domain.curio.dto.TagsDTO;
import pl.com.curiopedia.domain.curio.entity.Tag;
import pl.com.curiopedia.domain.curio.repository.TagRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by simple on 24.06.17.
 */
@Component
public class TagsFactoryImpl implements TagsFactory {

    private TagRepository tagRepository;

    @Autowired
    public TagsFactoryImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> makeSetOfTags(TagsDTO tagsDTO) {
        List<Tag> dbTags = tagRepository.findAll();
        Set<Tag> result = tagsDTO
                .getTags()
                .stream()
                .map(Tag::new).collect(Collectors.toSet());

        Set<Tag> resultDb = result.stream()
                .filter(t -> !dbTags.contains(t))
                .collect(Collectors.toSet());

        tagRepository.save(resultDb);

        return result;
    }
}
