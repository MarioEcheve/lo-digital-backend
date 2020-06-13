package backend.repository;

import backend.domain.UsuarioLibroPerfil;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsuarioLibroPerfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioLibroPerfilRepository extends JpaRepository<UsuarioLibroPerfil, Long> {

}
