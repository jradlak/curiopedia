package pl.com.curiopedia.domain.curio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.curio.dto.CategoryDTO;
import pl.com.curiopedia.domain.curio.entity.Category;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNameAlreadyExists;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.repository.CategoryRepository;

import java.util.Optional;

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
    public Category updateCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException,
            CategoryNameAlreadyExists {
        if (categoryNameExists(categoryDTO)) {
            throw new CategoryNameAlreadyExists("Category of name " + categoryDTO.getName() + " already exists!");
        }

        Category categoryToUpdate = this.categoryRepository.findOneById(categoryDTO.getId())
                .orElseThrow(CategoryNotFoundException::new);

        categoryToUpdate.setName(categoryDTO.getName());
        categoryToUpdate.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public Page<CategoryDTO> findAll(PageRequest pageable) {
        return categoryRepository.findAll(pageable)
                .map(c -> buildDTO(Optional.of(c)).get());
    }

    @Override
    public Optional<CategoryDTO> findOne(Long id) {
        return buildDTO(Optional.ofNullable(categoryRepository.findOne(id)));
    }

    @Override
    public Optional<CategoryDTO> findOneByName(String name) {
        return buildDTO(categoryRepository.findOneByName(name));
    }

    private Optional<CategoryDTO> buildDTO(Optional<Category> category) {
        return category.map(c -> CategoryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .build());
    }

    private boolean categoryNameExists(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findOneByName(categoryDTO.getName());
        return existingCategory.isPresent() && existingCategory.get().getId() != categoryDTO.getId();
    }
}
