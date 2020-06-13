package backend.web.rest;

import backend.domain.TipoMonto;
import backend.repository.TipoMontoRepository;
import backend.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link backend.domain.TipoMonto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoMontoResource {

    private final Logger log = LoggerFactory.getLogger(TipoMontoResource.class);

    private static final String ENTITY_NAME = "tipoMonto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoMontoRepository tipoMontoRepository;

    public TipoMontoResource(TipoMontoRepository tipoMontoRepository) {
        this.tipoMontoRepository = tipoMontoRepository;
    }

    /**
     * {@code POST  /tipo-montos} : Create a new tipoMonto.
     *
     * @param tipoMonto the tipoMonto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoMonto, or with status {@code 400 (Bad Request)} if the tipoMonto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-montos")
    public ResponseEntity<TipoMonto> createTipoMonto(@RequestBody TipoMonto tipoMonto) throws URISyntaxException {
        log.debug("REST request to save TipoMonto : {}", tipoMonto);
        if (tipoMonto.getId() != null) {
            throw new BadRequestAlertException("A new tipoMonto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMonto result = tipoMontoRepository.save(tipoMonto);
        return ResponseEntity.created(new URI("/api/tipo-montos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-montos} : Updates an existing tipoMonto.
     *
     * @param tipoMonto the tipoMonto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMonto,
     * or with status {@code 400 (Bad Request)} if the tipoMonto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoMonto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-montos")
    public ResponseEntity<TipoMonto> updateTipoMonto(@RequestBody TipoMonto tipoMonto) throws URISyntaxException {
        log.debug("REST request to update TipoMonto : {}", tipoMonto);
        if (tipoMonto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoMonto result = tipoMontoRepository.save(tipoMonto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoMonto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-montos} : get all the tipoMontos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoMontos in body.
     */
    @GetMapping("/tipo-montos")
    public List<TipoMonto> getAllTipoMontos() {
        log.debug("REST request to get all TipoMontos");
        return tipoMontoRepository.findAll();
    }

    /**
     * {@code GET  /tipo-montos/:id} : get the "id" tipoMonto.
     *
     * @param id the id of the tipoMonto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoMonto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-montos/{id}")
    public ResponseEntity<TipoMonto> getTipoMonto(@PathVariable Long id) {
        log.debug("REST request to get TipoMonto : {}", id);
        Optional<TipoMonto> tipoMonto = tipoMontoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoMonto);
    }

    /**
     * {@code DELETE  /tipo-montos/:id} : delete the "id" tipoMonto.
     *
     * @param id the id of the tipoMonto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-montos/{id}")
    public ResponseEntity<Void> deleteTipoMonto(@PathVariable Long id) {
        log.debug("REST request to delete TipoMonto : {}", id);
        tipoMontoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
