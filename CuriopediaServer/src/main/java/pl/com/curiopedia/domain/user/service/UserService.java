package pl.com.curiopedia.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.com.curiopedia.domain.user.dto.UserDTO;
import pl.com.curiopedia.domain.user.dto.UserParams;
import pl.com.curiopedia.domain.user.entity.User;

import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
public interface UserService extends UserDetailsService {
    Optional<UserDTO> findOne(Long id);

    Optional<UserDTO> findMe();

    Page<UserDTO> findAll(PageRequest pageable);

    User create(UserParams params);

    User update(UserParams params);

    User updateMe(UserParams params);

    void delete(UserParams params);
}
