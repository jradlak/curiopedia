package pl.com.curiopedia.domain.curio.factory;

import pl.com.curiopedia.domain.curio.dto.TagsDTO;
import pl.com.curiopedia.domain.curio.entity.Tag;

import java.util.Set;

/**
 * Created by jakub on 24.06.17.
 */
public interface TagsFactory {
    Set<Tag> makeSetOfTags(TagsDTO tagsDTO);
}
