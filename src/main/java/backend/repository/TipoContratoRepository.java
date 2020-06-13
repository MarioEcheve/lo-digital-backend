package backend.repository;

import backend.domain.TipoContrato;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoContrato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Long> {

}
