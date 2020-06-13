package backend.repository;

import backend.domain.EstadoServicio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoServicioRepository extends JpaRepository<EstadoServicio, Long> {

}
