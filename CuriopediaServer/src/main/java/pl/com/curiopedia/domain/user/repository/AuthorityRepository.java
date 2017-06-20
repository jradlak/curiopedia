package pl.com.curiopedia.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.user.entity.Authority;

import java.util.Optional;

/**
 * Created by jakub on 20.06.17.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findOneByName(String name);
}
