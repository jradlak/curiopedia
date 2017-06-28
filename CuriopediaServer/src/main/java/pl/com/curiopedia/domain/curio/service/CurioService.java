package pl.com.curiopedia.domain.curio.service;

import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException;
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException;

/**
 * Created by jakub on 23.06.17.
 */
public interface CurioService {
    Curio createCurio(CurioDTO curioParams) throws UserNotFoundException, SourceNotFoundException, CategoryNotFoundException;
}
