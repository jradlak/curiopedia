package pl.com.curiopedia.domain.curio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.curio.entity.Source;

import java.util.Optional;

/**
 * Created by jakub on 23.06.17.
 */
public interface SourceRepository extends JpaRepository<Source, Long> {
    Optional<Source> findOneByName(String name);
}
