package backend.repository;

import backend.domain.Dependencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import backend.domain.User;
/**
 * Spring Data  repository for the Dependencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Long> {
    @Query("select a from Dependencia a "
        +"inner join a.entidad b "
        +"where b.id = :idEntidad")
    List<Dependencia> buscaDependenciaPorEntidad(@Param("idEntidad") Long idEntidad);

    @Query(value = "select ud.id as id_usuario_dependencia ,'' as rut, '' as cargo, usr.first_name nombre,usr.last_name apellidos, pusr.nombre as perfil, ud.estado estado from dependencia d "+
                "inner join usuario_dependencia ud on ud.dependencia_id  = d.id "+
                "inner join jhi_user usr on usr.id = ud.usuario_id "+ 
                "left join perfil_usuario_dependencia pusr on pusr.id = ud.perfil_usuario_dependencia_id "+ 
                "where d.id = :idDependencia", nativeQuery = true)
    List<Map<String, String>> buscaUsuariosDependencia(@Param("idDependencia") Long idDependencia);
}
