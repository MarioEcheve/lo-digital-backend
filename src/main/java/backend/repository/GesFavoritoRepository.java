package backend.repository;

import backend.domain.GesFavorito;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GesFavorito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GesFavoritoRepository extends JpaRepository<GesFavorito, Long> {

}
