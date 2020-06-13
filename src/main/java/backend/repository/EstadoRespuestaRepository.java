package backend.repository;

import backend.domain.EstadoRespuesta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoRespuestaRepository extends JpaRepository<EstadoRespuesta, Long> {

}
