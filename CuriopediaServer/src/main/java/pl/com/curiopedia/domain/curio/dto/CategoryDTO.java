package pl.com.curiopedia.domain.curio.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
public class CategoryDTO {
    @NotNull
    private String name;

    private String description;
}
