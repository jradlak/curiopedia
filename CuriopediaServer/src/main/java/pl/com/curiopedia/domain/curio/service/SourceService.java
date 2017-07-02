package pl.com.curiopedia.domain.curio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.com.curiopedia.domain.curio.dto.SourceDTO;
import pl.com.curiopedia.domain.curio.entity.Source;

/**
 * Created by jakub on 02.07.17.
 */
public interface SourceService {
    Source createSource(SourceDTO sourceDTO);

    Page<SourceDTO> findAll(PageRequest pageable);
}
