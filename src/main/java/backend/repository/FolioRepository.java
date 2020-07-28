package backend.repository;

import backend.domain.Folio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Map;

/**
 * Spring Data  repository for the Folio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FolioRepository extends JpaRepository<Folio, Long> {
    @Query("select a from Folio a "
        +"inner join a.libro b "
        +"where b.id = :idLibro order by 1 desc")
    List<Folio> buscarFolioPorLibro(@Param("idLibro") Long idLibro);
    @Query( value = "select count(libro_id) + 1 as numero_folio from folio  where estado_folio = true and libro_id = :idLibro" ,nativeQuery = true)
     List<Map<String, String>> correlativoFolio(@Param("idLibro") Long idLibro);
     
    @Query(value = "select distinct folio from Folio folio left join fetch folio.folioReferencias",
        countQuery = "select count(distinct folio) from Folio folio")
    Page<Folio> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct folio from Folio folio left join fetch folio.folioReferencias")
    List<Folio> findAllWithEagerRelationships();

    @Query("select folio from Folio folio left join fetch folio.folioReferencias where folio.id =:id")
    Optional<Folio> findOneWithEagerRelationships(@Param("id") Long id);

}
