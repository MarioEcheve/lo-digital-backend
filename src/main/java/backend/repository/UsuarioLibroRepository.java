package backend.repository;

import backend.domain.UsuarioLibro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsuarioLibro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {

}
