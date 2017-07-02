package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.validation.constraints.NotNull;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class SourceDTO {
    @NotNull
    private String name;

    private String description;

    private String date;

    public SourceDTO(@JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("date") String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
