package pl.com.curiopedia.domain.curio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.curio.dto.CategoryDTO;
import pl.com.curiopedia.domain.curio.entity.Category;
import pl.com.curiopedia.domain.curio.repository.CategoryRepository;

/**
 * Created by jakub on 29.06.17.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        return categoryRepository.save(Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build());
    }

    @Override
    public Page<CategoryDTO> findAll(PageRequest pageable) {
        return categoryRepository.findAll(pageable)
                .map(c -> CategoryDTO.builder()
                    .name(c.getName())
                    .description(c.getDescription())
                    .build());
    }
}
