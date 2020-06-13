package backend.repository;

import backend.domain.UsuarioDependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsuarioDependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioDependenciaRepository extends JpaRepository<UsuarioDependencia, Long> {

}
