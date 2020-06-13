package backend.repository;

import backend.domain.ActividadRubro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActividadRubro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadRubroRepository extends JpaRepository<ActividadRubro, Long> {

}
