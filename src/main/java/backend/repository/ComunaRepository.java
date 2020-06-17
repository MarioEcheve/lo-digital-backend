package backend.repository;

import backend.domain.Comuna;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Comuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    @Query("select a from Comuna a "
        +"inner join a.region b "
        +"where b.id = :id")
    List<Comuna> buscaComunaPorRegion(@Param("id") Long id);
}
