package backend.repository;

import backend.domain.Archivo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;
/**
 * Spring Data  repository for the Archivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    @Query("select a from Archivo a "
        +"inner join a.folio b "
        +"where b.id = :idFolio")
    List<Archivo> buscaArchivosPorFolio(@Param("idFolio") Long idFolio);
}
