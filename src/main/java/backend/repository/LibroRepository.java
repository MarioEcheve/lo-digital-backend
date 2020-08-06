package backend.repository;

import backend.domain.Libro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
/**
 * Spring Data  repository for the Libro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("select a from Libro a "
        +"inner join a.contrato b "
        +"where b.id = :idContrato")
    List<Libro> buscarlibroPorContrato(@Param("idContrato") Long idContrato);

    @Query("select a from Libro a "
        +"inner join a.usuarioLibros b "
        +"inner join b.usuarioDependencia c "
        +"inner join c.usuario d "
        +"where d.id = :idUsuario")
    List<Libro> getMisLibros(@Param("idUsuario") Long idUsuario);
}
