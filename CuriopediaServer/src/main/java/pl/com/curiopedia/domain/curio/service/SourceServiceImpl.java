package pl.com.curiopedia.domain.curio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.com.curiopedia.domain.curio.dto.SourceDTO;
import pl.com.curiopedia.domain.curio.entity.Curio;
import pl.com.curiopedia.domain.curio.entity.Source;
import pl.com.curiopedia.domain.curio.repository.SourceRepository;

import java.util.stream.Collectors;

/**
 * Created by jakub on 02.07.17.
 */
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;

    public SourceServiceImpl(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public Source createSource(SourceDTO sourceDTO) {
        return sourceRepository.save(Source.builder()
            .date(sourceDTO.getDate())
            .name(sourceDTO.getName())
            .description(sourceDTO.getDescription())
            .build());
    }

    @Override
    public Page<SourceDTO> findAll(PageRequest pageable) {
        return sourceRepository.findAll(pageable)
                .map(s -> SourceDTO.builder()
                    .name(s.getName())
                    .description(s.getDescription())
                    .date(s.getDate())
                    .curios(s.getCurios().stream().map(Curio::toDTO).collect(Collectors.toList()))
                    .build());
    }
}
