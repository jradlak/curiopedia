package pl.com.curiopedia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.com.curiopedia.domain.user.dto.UserParams;
import pl.com.curiopedia.domain.user.entity.User;
import pl.com.curiopedia.domain.user.service.UserService;

import javax.validation.Valid;

/**
 * Created by jakub on 20.06.17.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid @RequestBody UserParams params) {
        return userService.create(params);
    }
}
