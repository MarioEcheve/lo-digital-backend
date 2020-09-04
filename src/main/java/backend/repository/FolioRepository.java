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

    @Query(value="select f2.* from folio f2 "+
                        "inner join usuario_libro ul on ul.id = f2.id_usuario_firma " +
                        "inner join usuario_dependencia ud on ud.id = ul.usuario_dependencia_id "+
                        "inner join jhi_user ju on ju.id = ud.usuario_id "+
                        "inner join usuario_libro ul2 on ul2.id = f2.id_receptor "+
                        "inner join usuario_dependencia ud2 on ud2.id = ul2.usuario_dependencia_id "+
                        "inner join jhi_user ju2 on ju2.id = ud2.usuario_id "+ 
                        "where  f2.libro_id = :idLibro "+
                                "and upper (ju.first_name) like %:emisor% "+
                                "and upper(ju2.first_name) like %:receptor% "+
                                "and upper(f2.asunto) like %:asunto% " +
                                "and f2.fecha_firma between '2020/07/28' and '2020/08/05'"+
                                "ORDER BY 1 DESC", nativeQuery = true)
    List<Map<String, String>> filtroFolioPersonalizado(
                                        @Param("idLibro") Long idLibro,
                                        @Param("emisor") String emisor,
                                        @Param("receptor") String receptor,
                                        @Param("asunto") String asunto);

    @Query("select a from Folio a "
    +"inner join a.gesFavoritos b "
    +"inner join b.usuarioLibro c "
    +"inner join a.libro d "
    +"where c.id = :idUsuarioLibro and d.id = :idLibro order by 1 desc")
    List<Folio> favoritosUsuarioLibro (@Param("idUsuarioLibro") Long idUsuarioLibro,@Param("idLibro") Long idLibro);

}
