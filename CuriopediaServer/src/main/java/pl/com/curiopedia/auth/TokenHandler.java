package pl.com.curiopedia.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.com.curiopedia.domain.user.entity.User;

import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
@Component
public interface TokenHandler {
    Optional<UserDetails> parseUserFromToken(String token);

    String createTokenForUser(User user);
}
