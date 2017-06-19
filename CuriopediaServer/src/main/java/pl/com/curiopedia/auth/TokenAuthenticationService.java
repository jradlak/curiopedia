package pl.com.curiopedia.auth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jakub on 19.06.17.
 */
public interface TokenAuthenticationService {
    Authentication getAuthentication(HttpServletRequest request);
}
