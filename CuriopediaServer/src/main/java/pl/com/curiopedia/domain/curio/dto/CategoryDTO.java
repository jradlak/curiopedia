package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class CategoryDTO {
    @NotNull
    private String name;

    private String description;

    public CategoryDTO(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
