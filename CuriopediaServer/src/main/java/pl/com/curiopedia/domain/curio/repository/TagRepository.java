package pl.com.curiopedia.domain.curio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.curio.entity.Tag;

/**
 * Created by jakub on 23.06.17.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
