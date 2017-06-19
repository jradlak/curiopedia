package pl.com.curiopedia.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.user.entity.User;
import pl.com.curiopedia.domain.user.repository.UserRepository;

import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
@Service
public class SecurityContextServiceImpl implements SecurityContextService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usr =  userRepository.findOneByUsername(authentication.getName());
        return usr;
    }
}
