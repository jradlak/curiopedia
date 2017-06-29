package pl.com.curiopedia.domain.curio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

/**
 * Created by jakub on 23.06.17.
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class TagsDTO {
    private Set<String> tags;

    public TagsDTO(@JsonProperty("tags") Set<String> tags) {
        this.tags = tags;
    }
}
