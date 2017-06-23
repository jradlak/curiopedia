package pl.com.curiopedia.domain.curio.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
public class CategoryDTO {
    private String name;

    private String description;
}
