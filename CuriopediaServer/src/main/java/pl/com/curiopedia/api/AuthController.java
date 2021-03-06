package pl.com.curiopedia.api;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.com.curiopedia.auth.TokenHandler;
import pl.com.curiopedia.domain.user.entity.Authority;
import pl.com.curiopedia.domain.user.entity.User;
import pl.com.curiopedia.domain.user.service.SecurityContextService;

import java.io.Serializable;

/**
 * Created by jakub on 19.06.17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;
    private final SecurityContextService securityContextService;

    @Autowired
    AuthController(AuthenticationManager authenticationManager,
                   TokenHandler tokenHandler,
                   SecurityContextService securityContextService) {
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.securityContextService = securityContextService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AuthResponse auth(@RequestBody AuthParams params) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
        final Authentication authentication = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            final String role = determineRole(u);
            return new AuthResponse(token, role);
        }).orElseThrow(RuntimeException::new);
    }

    @Value
    private static final class AuthParams {
        private final String email;
        private final String password;

        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static final class AuthResponse implements Serializable {
        private String token;
        private String role;
    }

    private String determineRole(User user) {
        if (!determineRole(user, Authority.ROLE_ADMIN).isEmpty()) {
            return Authority.ROLE_ADMIN;
        }

        if (!determineRole(user, Authority.ROLE_AUTHOR).isEmpty()) {
            return Authority.ROLE_AUTHOR;
        }

        if (!determineRole(user, Authority.ROLE_GUEST).isEmpty()) {
            return Authority.ROLE_GUEST;
        }

        return Authority.ROLE_GUEST;
    }

    private String determineRole(User user, String role) {
        if (user.getAuthorities().stream()
                .filter(a -> role.equals(a.getAuthority())).count() > 0) {
            return role;
        }

        return "";
    }
}
