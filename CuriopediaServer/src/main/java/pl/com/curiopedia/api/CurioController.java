package pl.com.curiopedia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException;
import pl.com.curiopedia.domain.curio.service.CurioService;
import pl.com.curiopedia.domain.user.dto.ErrorResponse;
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException;

import javax.validation.Valid;

/**
 * Created by jakub on 28.06.17.
 */
@RestController
@RequestMapping("/api/curios")
public class CurioController {

    private final CurioService curioService;

    @Autowired
    public CurioController(CurioService curioService) {
        this.curioService = curioService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@Valid @RequestBody CurioDTO curioDTO) throws UserNotFoundException,
            SourceNotFoundException, CategoryNotFoundException {
        curioService.createCurio(curioDTO);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.NO_USER)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse(ErrorResponse.NO_USER, ErrorResponse.NO_USER_MSG);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.NO_SOURCE)
    @ExceptionHandler(SourceNotFoundException.class)
    public ErrorResponse handleSourceNotFound() {
        return new ErrorResponse(ErrorResponse.NO_SOURCE, ErrorResponse.NO_SOURCE_MSG);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.NO_CATEGORY)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleCategoryNotFound() {
        return new ErrorResponse(ErrorResponse.NO_SOURCE, ErrorResponse.NO_SOURCE_MSG);
    }
}
