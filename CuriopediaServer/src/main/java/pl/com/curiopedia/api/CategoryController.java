package pl.com.curiopedia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.curiopedia.domain.curio.dto.CategoryDTO;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNameAlreadyExists;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.service.CategoryService;
import pl.com.curiopedia.domain.user.dto.ErrorResponse;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by jakub on 30.06.17.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id:\\d+}")
    public CategoryDTO show(@PathVariable("id") Long id) throws CategoryNotFoundException {
        return categoryService.findOne(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/update")
    public void update(@Valid @RequestBody CategoryDTO categoryDTO)
            throws CategoryNotFoundException, CategoryNameAlreadyExists {
        categoryService.updateCategory(categoryDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<CategoryDTO> list(@RequestParam(value = "page", required = false) @Nullable Integer page,
                                  @RequestParam(value = "size", required = false) @Nullable Integer size) {
        final PageRequest pageable = new PageRequest(
                Optional.ofNullable(page).orElse(1) - 1,
                Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE));

        return categoryService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNameAlreadyExists.class)
    public ErrorResponse handleCategoryNameAlreadyExists(CategoryNameAlreadyExists e) {
        return new ErrorResponse(ErrorResponse.CATEGORY_NAME_ALREADY_EXISTS,
                ErrorResponse.CATEGORY_NAME_ALREADY_EXISTS_MSG);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNameAlreadyExists.class)
    public ErrorResponse handleCategoryNotFound(CategoryNotFoundException e) {
        return new ErrorResponse(ErrorResponse.NO_CATEGORY, ErrorResponse.NO_CATEGORY_MSG);
    }
}
