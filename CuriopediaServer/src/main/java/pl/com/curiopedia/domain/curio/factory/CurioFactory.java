package pl.com.curiopedia.domain.curio.factory;

import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;

/**
 * Created by simple on 24.06.17.
 */
public interface CurioFactory {
    Curio makeCurio(CurioDTO curioDTO);
}
