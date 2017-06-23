package pl.com.curiopedia.domain.curio.service;

import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;

/**
 * Created by jakub on 23.06.17.
 */
public interface CurioService {
    Curio createCurio(CurioDTO curioParams);
}
