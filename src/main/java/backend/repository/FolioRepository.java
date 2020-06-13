package backend.repository;

import backend.domain.Folio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Folio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FolioRepository extends JpaRepository<Folio, Long> {

}
