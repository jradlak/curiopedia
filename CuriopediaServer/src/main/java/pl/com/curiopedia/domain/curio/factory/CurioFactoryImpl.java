package pl.com.curiopedia.domain.curio.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException;
import pl.com.curiopedia.domain.curio.repository.CategoryRepository;
import pl.com.curiopedia.domain.curio.repository.SourceRepository;
import pl.com.curiopedia.domain.user.repository.UserRepository;
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException;

import java.time.LocalDateTime;

/**
 * Created by jakub on 24.06.17.
 */
@Component
public class CurioFactoryImpl implements CurioFactory {

    private CategoryRepository categoryRepository;

    private SourceRepository sourceRepository;

    private UserRepository userRepository;

    private TagsFactory tagsFactory;

    @Autowired
    public CurioFactoryImpl(CategoryRepository categoryRepository,
                            SourceRepository sourceRepository,
                            UserRepository userRepository, TagsFactory tagsFactory) {
        this.categoryRepository = categoryRepository;
        this.sourceRepository = sourceRepository;
        this.userRepository = userRepository;
        this.tagsFactory = tagsFactory;
    }

    @Override
    public Curio makeCurio(CurioDTO curioDTO) throws UserNotFoundException,
            CategoryNotFoundException, SourceNotFoundException {
        Curio curio = Curio.builder()
                .author(userRepository.findOneByUsername(curioDTO.getAuthor())
                        .orElseThrow(UserNotFoundException::new))
                .category(categoryRepository.findOneByName(curioDTO.getCategory())
                        .orElseThrow(CategoryNotFoundException::new))
                .source(sourceRepository.findOneByName(curioDTO.getSource())
                        .orElseThrow(SourceNotFoundException::new))
                .tags(tagsFactory.makeSetOfTags(curioDTO.getTags()))
                .title(curioDTO.getTitle())
                .content(curioDTO.getContent())
                .description(curioDTO.getDescription())
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();

        return curio;
    }
}
