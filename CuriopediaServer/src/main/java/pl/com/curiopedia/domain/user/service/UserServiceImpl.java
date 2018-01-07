package pl.com.curiopedia.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.com.curiopedia.domain.user.dto.UserDTO;
import pl.com.curiopedia.domain.user.dto.UserParams;
import pl.com.curiopedia.domain.user.entity.Authority;
import pl.com.curiopedia.domain.user.entity.User;
import pl.com.curiopedia.domain.user.repository.AuthorityRepository;
import pl.com.curiopedia.domain.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<UserDTO> findOne(Long id) {
        return find(Optional.ofNullable(userRepository.findOne(id)));
    }

    @Override
    public Optional<UserDTO> findMe() {
        return securityContextService.currentUser().flatMap(u -> findOne(u.getId()));
    }

    @Override
    public Page<UserDTO> findAll(PageRequest pageable) {
        return userRepository.findAll(pageable).map(u -> UserDTO.builder()
                .id(u.getId())
                .username(u.getName())
                .name(u.getName())
                .email(u.getUsername())
                .authority(makeAuthority(u.getAuthorities()))
                .build()
        );
    }

    @Override
    public User create(UserParams params) {
        List<Authority> authorities = authorityRepository.findAll();
        return userRepository.save(params.toUser(authorities));
    }

    @Override
    public User update(UserParams params) {
        User user = userRepository.findOneByUsername(params.getEmail().get())
                .orElseThrow(() -> new UsernameNotFoundException("user not found."));

        params.getEmail().ifPresent(user::setUsername);
        params.getEncodedPassword().ifPresent(user::setPassword);
        params.getName().ifPresent(user::setName);

        User modUser = params.toUser(authorityRepository.findAll());
        user.setAuthorities(modUser
                .getAuthorities()
                .stream()
                .map(a -> new Authority(a.getAuthority()))
                .collect(Collectors.toSet()));

        return userRepository.save(user);
    }

    @Override
    public User updateMe(UserParams params) {
        return securityContextService.currentUser()
                .map(u -> update(params))
                .orElseThrow(() -> new AccessDeniedException(""));
    }

    @Override
    public void delete(UserParams userParams) {
        User user = getUser(userParams);
        Set<Authority> auths = user.getAuths();

        if (auths != null) {
            for (Authority a : auths) {
                authorityRepository.delete(a);
            }

            user.setAuthorities(new HashSet<>());
        }

        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findOneByUsername(username);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    private String makeAuthority(Collection<? extends GrantedAuthority> a) {
        Set<String> result = a.stream()
                .map(e -> e.getAuthority())
                .collect(Collectors.toSet());

        if (result.contains(Authority.ROLE_ADMIN)) {
            return Authority.ROLE_ADMIN;
        } else if (result.contains(Authority.ROLE_AUTHOR)) {
            return Authority.ROLE_AUTHOR;
        } else {
            return Authority.ROLE_GUEST;
        }
    }

    private Optional<UserDTO> find(Optional<User> usr) {
        return usr.map(
                u -> UserDTO.builder()
                        .id(u.getId())
                        .email(u.getUsername())
                        .username(u.getName())
                        .authority(makeAuthority(u.getAuthorities()))
                        .build());
    }

    private User getUser(UserParams userParams) {
        UsernameNotFoundException e = new UsernameNotFoundException("user not found.");
        return userRepository.findOneByUsername(userParams.getEmail().get()).orElseThrow(() -> e);
    }
}
