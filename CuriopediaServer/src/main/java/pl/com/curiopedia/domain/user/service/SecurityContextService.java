package pl.com.curiopedia.domain.user.service;

import pl.com.curiopedia.domain.user.entity.User;

import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
public interface SecurityContextService {
    Optional<User> currentUser();
}
