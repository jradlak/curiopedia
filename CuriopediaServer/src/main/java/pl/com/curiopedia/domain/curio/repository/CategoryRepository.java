package pl.com.curiopedia.domain.curio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.curiopedia.domain.curio.entity.Category;

import java.util.Optional;

/**
 * Created by jakub on 23.06.17.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findOneById(Long id);
    Optional<Category> findOneByName(String name);
}
