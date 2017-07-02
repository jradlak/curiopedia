package pl.com.curiopedia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.com.curiopedia.domain.curio.dto.SourceDTO;
import pl.com.curiopedia.domain.curio.service.SourceService;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by jakub on 02.07.17.
 */
@RestController
@RequestMapping("/api/sources")
public class SourceController {
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@Valid @RequestBody SourceDTO sourceDTO) {
        sourceService.createSource(sourceDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<SourceDTO> list(@RequestParam(value = "page", required = false) @Nullable Integer page,
                                @RequestParam(value = "size", required = false) @Nullable Integer size) {
        final PageRequest pageable = new PageRequest(
                Optional.ofNullable(page).orElse(1) - 1,
                Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE));

        return sourceService.findAll(pageable);
    }
}
