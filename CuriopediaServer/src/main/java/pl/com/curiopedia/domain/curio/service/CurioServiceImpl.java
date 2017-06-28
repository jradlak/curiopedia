package pl.com.curiopedia.domain.curio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.exceptions.CategoryNotFoundException;
import pl.com.curiopedia.domain.curio.exceptions.SourceNotFoundException;
import pl.com.curiopedia.domain.curio.factory.CurioFactory;
import pl.com.curiopedia.domain.user.service.exceptions.UserNotFoundException;

/**
 * Created by jakub on 23.06.17.
 */
@Service
public class CurioServiceImpl implements CurioService {

    private CurioFactory curioFactory;

    @Autowired
    public CurioServiceImpl(CurioFactory curioFactory) {
        this.curioFactory = curioFactory;
    }

    @Override
    public Curio createCurio(CurioDTO curioParams) throws UserNotFoundException,
            SourceNotFoundException, CategoryNotFoundException {
        //TODO
        return curioFactory.makeCurio(curioParams);
    }
}
