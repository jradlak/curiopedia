package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

    private String creationDate;

    private String modificationDate;

    public CurioDTO(@JsonProperty("title") String title,
                    @JsonProperty("description") String description,
                    @JsonProperty("content") String content,
                    @JsonProperty("tags") TagsDTO tags,
                    @JsonProperty("author") String author,
                    @JsonProperty("category") String category,
                    @JsonProperty("source") String source,
                    @JsonProperty("creationDate") String creationDate,
                    @JsonProperty("modificationDate") String modificationDate) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
        this.author = author;
        this.category = category;
        this.source = source;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }
}

