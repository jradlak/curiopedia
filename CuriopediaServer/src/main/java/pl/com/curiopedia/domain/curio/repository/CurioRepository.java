package pl.com.curiopedia.domain.curio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.curio.entity.Curio;

/**
 * Created by jakub on 23.06.17.
 */
public interface CurioRepository extends JpaRepository<Curio, Long> {
}
