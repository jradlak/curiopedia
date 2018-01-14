package pl.com.curiopedia.domain.curio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.com.curiopedia.domain.curio.dto.CategoryDTO;
import pl.com.curiopedia.domain.curio.entity.Category;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNameAlreadyExists;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;

import java.util.Optional;

/**
 * Created by jakub on 29.06.17.
 */
public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException, CategoryNameAlreadyExists;

    Page<CategoryDTO> findAll(PageRequest pageable);

    Optional<CategoryDTO> findOne(Long id);

    Optional<CategoryDTO> findOneByName(String name);
}
