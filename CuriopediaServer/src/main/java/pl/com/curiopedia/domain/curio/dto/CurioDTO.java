package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;
import pl.com.curiopedia.domain.user.dto.UserDTO;
import pl.com.curiopedia.tools.LocalDateDeserializer;
import pl.com.curiopedia.tools.LocalDateSerializer;

import java.time.LocalDate;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
public class CurioDTO {

    private String title;

    private String description;

    private String content;

    private TagsDTO tags;

    private UserDTO author;

    private CategoryDTO category;

    private SourceDTO source;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;
}
