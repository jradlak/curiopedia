package pl.com.curiopedia.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.user.entity.User;

import java.util.Optional;

/**
 * Created by jakub on 19.06.17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
}
