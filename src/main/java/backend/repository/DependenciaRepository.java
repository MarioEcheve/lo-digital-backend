package backend.repository;

import backend.domain.Dependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Long> {

}
