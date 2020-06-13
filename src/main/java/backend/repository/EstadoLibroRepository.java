package backend.repository;

import backend.domain.EstadoLibro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoLibro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoLibroRepository extends JpaRepository<EstadoLibro, Long> {

}
