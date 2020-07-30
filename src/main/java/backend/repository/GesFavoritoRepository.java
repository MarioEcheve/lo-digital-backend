package backend.repository;

import backend.domain.GesFavorito;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
/**
 * Spring Data  repository for the GesFavorito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GesFavoritoRepository extends JpaRepository<GesFavorito, Long> {
    @Query("select a from GesFavorito a "
        +"inner join a.folio b "
        +"where b.id = :idFolio")
    List<GesFavorito> BuscarFavoritoByFolio(@Param("idFolio") Long idFolio);

    @Query("select a from GesFavorito a "
        +"inner join a.folio b "
        +"inner join a.usuarioLibro c "
        +"where c.id = :idUsuario")
    List<GesFavorito> BuscarFavoritoByUsuario(@Param("idUsuario") Long idUsuario);
}