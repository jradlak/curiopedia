package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import pl.com.curiopedia.tools.LocalDateDeserializer;
import pl.com.curiopedia.tools.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
@Getter
public class SourceDTO {
    @NotNull
    private String name;

    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private List<CurioDTO> curios;

    public SourceDTO(@JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("date") LocalDate date,
                     @JsonProperty("curios") List<CurioDTO> curios) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.curios = curios;
    }
}
