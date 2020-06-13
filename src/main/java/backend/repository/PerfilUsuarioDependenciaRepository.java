package backend.repository;

import backend.domain.PerfilUsuarioDependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PerfilUsuarioDependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilUsuarioDependenciaRepository extends JpaRepository<PerfilUsuarioDependencia, Long> {

}
