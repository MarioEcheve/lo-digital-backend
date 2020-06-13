package backend.repository;

import backend.domain.TipoFolio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoFolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoFolioRepository extends JpaRepository<TipoFolio, Long> {

}
