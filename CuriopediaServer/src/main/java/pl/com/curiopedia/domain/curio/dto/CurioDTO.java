package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import pl.com.curiopedia.domain.curio.entity.Category;
import pl.com.curiopedia.domain.user.dto.UserDTO;
import pl.com.curiopedia.tools.LocalDateDeserializer;
import pl.com.curiopedia.tools.LocalDateSerializer;

import java.time.LocalDateTime;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class CurioDTO {

    private String title;

    private String description;

    private String content;

    private TagsDTO tags;

    private String author;

    private String category;

    private String source;

    public CurioDTO(@JsonProperty("title") String title,
                    @JsonProperty("description") String description,
                    @JsonProperty("content") String content,
                    @JsonProperty("tags") TagsDTO tags,
                    @JsonProperty("author") String author,
                    @JsonProperty("category") String category,
                    @JsonProperty("source") String source) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
        this.author = author;
        this.category = category;
        this.source = source;
    }
}

