package pl.com.curiopedia.domain.user.dto;

import lombok.Value;

/**
 * Created by jakub on 22.06.17.
 */
@Value
public class ErrorResponse {
    private final String code;
    private final String message;
}