package backend.repository;

import backend.domain.TipoUsuarioDependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoUsuarioDependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoUsuarioDependenciaRepository extends JpaRepository<TipoUsuarioDependencia, Long> {

}
