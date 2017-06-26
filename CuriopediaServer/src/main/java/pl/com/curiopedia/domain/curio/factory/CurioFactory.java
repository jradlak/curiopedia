package pl.com.curiopedia.domain.curio.factory;

import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException;
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException;

/**
 * Created by jakub on 24.06.17.
 */
public interface CurioFactory {
    Curio makeCurio(CurioDTO curioDTO) throws UserNotFoundException, CategoryNotFoundException, SourceNotFoundException;
}
