package backend.repository;

import backend.domain.Folio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
/**
 * Spring Data  repository for the Folio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FolioRepository extends JpaRepository<Folio, Long> {
    @Query("select a from Folio a "
        +"inner join a.libro b "
        +"where b.id = :idLibro")
    List<Folio> buscarFolioPorLibro(@Param("idLibro") Long idLibro);
}
