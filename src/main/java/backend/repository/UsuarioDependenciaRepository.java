package backend.repository;

import backend.domain.UsuarioDependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UsuarioDependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioDependenciaRepository extends JpaRepository<UsuarioDependencia, Long> {

    @Query("select usuarioDependencia from UsuarioDependencia usuarioDependencia where usuarioDependencia.usuario.login = ?#{principal.username}")
    List<UsuarioDependencia> findByUsuarioIsCurrentUser();

}
