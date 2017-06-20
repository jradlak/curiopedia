package pl.com.curiopedia.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.com.curiopedia.domain.user.entity.Authority;
import pl.com.curiopedia.domain.user.entity.User;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jakub on 20.06.17.
 */
@ToString
@EqualsAndHashCode
public class UserParams {
    private final String email;
    @Size(min = 8, max = 100)
    private final String password;
    private final String name;

    @Getter
    private final String authority;

    public UserParams(@JsonProperty("email") String email,
                      @JsonProperty("password") String password,
                      @JsonProperty("name") String name,
                      @JsonProperty("authority") String authority) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authority = authority;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getEncodedPassword() {
        return Optional.ofNullable(password).map(p -> new BCryptPasswordEncoder().encode(p));
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getAuthority() {
        return Optional.ofNullable(authority);
    }

    public User toUser(List<Authority> authorities) {
        User user = new User();
        user.setUsername(email);
        if (password != null && !password.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }

        user.setName(name);
        user.setAuthorities(makeAuthoritiesCollection(authorities));

        return user;
    }

    private Set<Authority> makeAuthoritiesCollection(List<Authority> authorities) {
        Set<Authority> uAuthorities = new HashSet<>();
        Authority authAdmin = authorities.stream().filter(a -> a.getAuthority().equals(Authority.ROLE_ADMIN)).findFirst().get();
        Authority authUser = authorities.stream().filter(a -> a.getAuthority().equals(Authority.ROLE_AUTHOR)).findFirst().get();
        Authority authGuest = authorities.stream().filter(a -> a.getAuthority().equals(Authority.ROLE_GUEST)).findFirst().get();

        if (getAuthority().isPresent()) {
            if (Authority.ROLE_ADMIN.equals(authority)) {
                uAuthorities.add(authAdmin);
                uAuthorities.add(authUser);
                uAuthorities.add(authGuest);
            } else if (Authority.ROLE_AUTHOR.equals(authority)) {
                uAuthorities.add(authUser);
                uAuthorities.add(authGuest);
            } else if (Authority.ROLE_GUEST.equals(authority)) {
                uAuthorities.add(authGuest);
            }
        }

        return uAuthorities;
    }
}
