package pl.com.curiopedia.domain.user.dto;

import lombok.Value;

/**
 * Created by jakub on 22.06.17.
 */
@Value
public class ErrorResponse {

    public final static String EMAIL_TAKEN = "email_already_taken";

    public final static String EMAIL_TAKEN_MSG = "This email is already taken.";


    public final static String NO_USER = "no_user";

    public final static String NO_USER_MSG = "User not found.";


    public final static String NO_CATEGORY = "no_category";

    public final static String NO_CATEGORY_MSG = "Category not found";

    public final static String CATEGORY_NAME_ALREADY_EXISTS = "category_name_already_exists";

    public final static String CATEGORY_NAME_ALREADY_EXISTS_MSG = "Category name already exists";

    public final static String NO_SOURCE = "no_source";

    public final static String NO_SOURCE_MSG = "Source not found";

    private final String code;
    private final String message;
}