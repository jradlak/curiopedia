package pl.com.curiopedia.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.user.dto.UserParams;
import pl.com.curiopedia.domain.user.entity.Authority;
import pl.com.curiopedia.domain.user.entity.User;
import pl.com.curiopedia.domain.user.repository.AuthorityRepository;
import pl.com.curiopedia.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public UserServiceImpl(AuthorityRepository authorityRepository,
                           UserRepository userRepository,
                           SecurityContextService securityContextService) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findOneByUsername(username);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    @Override
    public User create(UserParams params) {
        List<Authority> authorities = authorityRepository.findAll();
        return userRepository.save(params.toUser(authorities));
    }
}
