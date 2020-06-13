package backend.web.rest;

import backend.domain.TipoFolio;
import backend.repository.TipoFolioRepository;
import backend.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link backend.domain.TipoFolio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoFolioResource {

    private final Logger log = LoggerFactory.getLogger(TipoFolioResource.class);

    private static final String ENTITY_NAME = "tipoFolio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoFolioRepository tipoFolioRepository;

    public TipoFolioResource(TipoFolioRepository tipoFolioRepository) {
        this.tipoFolioRepository = tipoFolioRepository;
    }

    /**
     * {@code POST  /tipo-folios} : Create a new tipoFolio.
     *
     * @param tipoFolio the tipoFolio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoFolio, or with status {@code 400 (Bad Request)} if the tipoFolio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-folios")
    public ResponseEntity<TipoFolio> createTipoFolio(@Valid @RequestBody TipoFolio tipoFolio) throws URISyntaxException {
        log.debug("REST request to save TipoFolio : {}", tipoFolio);
        if (tipoFolio.getId() != null) {
            throw new BadRequestAlertException("A new tipoFolio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoFolio result = tipoFolioRepository.save(tipoFolio);
        return ResponseEntity.created(new URI("/api/tipo-folios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-folios} : Updates an existing tipoFolio.
     *
     * @param tipoFolio the tipoFolio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoFolio,
     * or with status {@code 400 (Bad Request)} if the tipoFolio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoFolio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-folios")
    public ResponseEntity<TipoFolio> updateTipoFolio(@Valid @RequestBody TipoFolio tipoFolio) throws URISyntaxException {
        log.debug("REST request to update TipoFolio : {}", tipoFolio);
        if (tipoFolio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoFolio result = tipoFolioRepository.save(tipoFolio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoFolio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-folios} : get all the tipoFolios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoFolios in body.
     */
    @GetMapping("/tipo-folios")
    public List<TipoFolio> getAllTipoFolios() {
        log.debug("REST request to get all TipoFolios");
        return tipoFolioRepository.findAll();
    }

    /**
     * {@code GET  /tipo-folios/:id} : get the "id" tipoFolio.
     *
     * @param id the id of the tipoFolio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoFolio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-folios/{id}")
    public ResponseEntity<TipoFolio> getTipoFolio(@PathVariable Long id) {
        log.debug("REST request to get TipoFolio : {}", id);
        Optional<TipoFolio> tipoFolio = tipoFolioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoFolio);
    }

    /**
     * {@code DELETE  /tipo-folios/:id} : delete the "id" tipoFolio.
     *
     * @param id the id of the tipoFolio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-folios/{id}")
    public ResponseEntity<Void> deleteTipoFolio(@PathVariable Long id) {
        log.debug("REST request to delete TipoFolio : {}", id);
        tipoFolioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
