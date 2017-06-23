package pl.com.curiopedia.domain.curio.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
public class TagsDTO {
    private Set<String> tags;
}
