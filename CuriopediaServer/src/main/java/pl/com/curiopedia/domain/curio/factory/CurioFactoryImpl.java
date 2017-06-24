package pl.com.curiopedia.domain.curio.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.repository.CategoryRepository;
import pl.com.curiopedia.domain.curio.repository.CurioRepository;
import pl.com.curiopedia.domain.curio.repository.SourceRepository;

/**
 * Created by simple on 24.06.17.
 */
@Component
public class CurioFactoryImpl implements CurioFactory {

    private CurioRepository curioRepository;

    private CategoryRepository categoryRepository;

    private SourceRepository sourceRepository;

    private TagsFactory tagsFactory;

    @Autowired
    public CurioFactoryImpl(CurioRepository curioRepository, CategoryRepository categoryRepository,
                            SourceRepository sourceRepository, TagsFactory tagsFactory) {
        this.curioRepository = curioRepository;
        this.categoryRepository = categoryRepository;
        this.sourceRepository = sourceRepository;
        this.tagsFactory = tagsFactory;
    }

    @Override
    public Curio makeCurio(CurioDTO curioDTO) {
        return null;
    }
}
