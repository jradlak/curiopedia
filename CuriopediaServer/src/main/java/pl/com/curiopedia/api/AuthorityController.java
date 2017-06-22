package pl.com.curiopedia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.com.curiopedia.domain.user.dto.AuthorityDTO;
import pl.com.curiopedia.domain.user.service.AuthorityService;

import java.util.List;

/**
 * Created by jakub on 22.06.17.
 */
@RestController
public class AuthorityController {
    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/users/authorities")
    public List<AuthorityDTO> allAuthorities() {
        return authorityService.findAllAuthorities();
    }
}