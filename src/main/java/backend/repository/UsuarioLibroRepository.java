package backend.repository;

import backend.domain.UsuarioLibro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
/**
 * Spring Data  repository for the UsuarioLibro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioLibroRepository extends JpaRepository<UsuarioLibro, Long> {
   @Query("select a from UsuarioLibro a "
            +"inner join a.usuarioDependencia b "
            +" inner join b.usuario c "
             +" inner join a.libro d "
            +"where d.id = :idLibro and c.id = :idUsuario")
    List<UsuarioLibro> ListaUsuariosLibrosFolio (@Param("idLibro") Long idLibro,@Param("idUsuario") Long idUsuario);

    @Query("select a from UsuarioLibro a "
    +"inner join a.usuarioDependencia b "
    +" inner join b.usuario c "
     +" inner join a.libro d "
    +"where d.id = :idLibro and c.id != :idUsuario")
List<UsuarioLibro> ListaUsuariosLibros (@Param("idLibro") Long idLibro,@Param("idUsuario") Long idUsuario);
}
