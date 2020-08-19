package backend.repository;

import backend.domain.Entidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
/**
 * Spring Data  repository for the Entidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Long> {
  @Query("select a from Entidad a "
        +"inner join a.dependencias b "
        +"inner join b.usuarioDependencias c  "
        +"inner join c.usuario d "
        +"where d.id = :idUsuario")
  List<Entidad> buscaEntidadPorUsuario(@Param("idUsuario") Long idUsuario);
}
