package pl.com.curiopedia.domain.user.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Created by jakub on 22.06.17.
 */
@Value
@Builder
public class UserDTO {
    private final long id;
    private final String email;
    @NonNull
    private final String username;
    @NotNull
    private final String name;
    @NonNull
    private final String authority;
}