package pl.com.curiopedia.domain.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.com.curiopedia.domain.user.dto.UserParams;
import pl.com.curiopedia.domain.user.entity.User;

/**
 * Created by jakub on 19.06.17.
 */
public interface UserService extends UserDetailsService {
    User create(UserParams params);
}
