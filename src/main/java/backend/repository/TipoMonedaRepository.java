package backend.repository;

import backend.domain.TipoMoneda;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoMoneda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoMonedaRepository extends JpaRepository<TipoMoneda, Long> {

}
