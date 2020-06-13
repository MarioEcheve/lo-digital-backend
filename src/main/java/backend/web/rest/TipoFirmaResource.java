package backend.web.rest;

import backend.domain.TipoFirma;
import backend.repository.TipoFirmaRepository;
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
 * REST controller for managing {@link backend.domain.TipoFirma}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoFirmaResource {

    private final Logger log = LoggerFactory.getLogger(TipoFirmaResource.class);

    private static final String ENTITY_NAME = "tipoFirma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoFirmaRepository tipoFirmaRepository;

    public TipoFirmaResource(TipoFirmaRepository tipoFirmaRepository) {
        this.tipoFirmaRepository = tipoFirmaRepository;
    }

    /**
     * {@code POST  /tipo-firmas} : Create a new tipoFirma.
     *
     * @param tipoFirma the tipoFirma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoFirma, or with status {@code 400 (Bad Request)} if the tipoFirma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-firmas")
    public ResponseEntity<TipoFirma> createTipoFirma(@RequestBody TipoFirma tipoFirma) throws URISyntaxException {
        log.debug("REST request to save TipoFirma : {}", tipoFirma);
        if (tipoFirma.getId() != null) {
            throw new BadRequestAlertException("A new tipoFirma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoFirma result = tipoFirmaRepository.save(tipoFirma);
        return ResponseEntity.created(new URI("/api/tipo-firmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-firmas} : Updates an existing tipoFirma.
     *
     * @param tipoFirma the tipoFirma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoFirma,
     * or with status {@code 400 (Bad Request)} if the tipoFirma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoFirma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-firmas")
    public ResponseEntity<TipoFirma> updateTipoFirma(@RequestBody TipoFirma tipoFirma) throws URISyntaxException {
        log.debug("REST request to update TipoFirma : {}", tipoFirma);
        if (tipoFirma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoFirma result = tipoFirmaRepository.save(tipoFirma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoFirma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-firmas} : get all the tipoFirmas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoFirmas in body.
     */
    @GetMapping("/tipo-firmas")
    public List<TipoFirma> getAllTipoFirmas() {
        log.debug("REST request to get all TipoFirmas");
        return tipoFirmaRepository.findAll();
    }

    /**
     * {@code GET  /tipo-firmas/:id} : get the "id" tipoFirma.
     *
     * @param id the id of the tipoFirma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoFirma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-firmas/{id}")
    public ResponseEntity<TipoFirma> getTipoFirma(@PathVariable Long id) {
        log.debug("REST request to get TipoFirma : {}", id);
        Optional<TipoFirma> tipoFirma = tipoFirmaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoFirma);
    }

    /**
     * {@code DELETE  /tipo-firmas/:id} : delete the "id" tipoFirma.
     *
     * @param id the id of the tipoFirma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-firmas/{id}")
    public ResponseEntity<Void> deleteTipoFirma(@PathVariable Long id) {
        log.debug("REST request to delete TipoFirma : {}", id);
        tipoFirmaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
