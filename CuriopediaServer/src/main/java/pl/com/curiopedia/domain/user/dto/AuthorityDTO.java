package pl.com.curiopedia.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Created by jakub on 22.06.17.
 */
@Value
@AllArgsConstructor
public class AuthorityDTO {
    private String name;
}