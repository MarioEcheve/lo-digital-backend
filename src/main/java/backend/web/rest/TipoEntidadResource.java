package backend.web.rest;

import backend.domain.TipoEntidad;
import backend.repository.TipoEntidadRepository;
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
 * REST controller for managing {@link backend.domain.TipoEntidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoEntidadResource {

    private final Logger log = LoggerFactory.getLogger(TipoEntidadResource.class);

    private static final String ENTITY_NAME = "tipoEntidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEntidadRepository tipoEntidadRepository;

    public TipoEntidadResource(TipoEntidadRepository tipoEntidadRepository) {
        this.tipoEntidadRepository = tipoEntidadRepository;
    }

    /**
     * {@code POST  /tipo-entidads} : Create a new tipoEntidad.
     *
     * @param tipoEntidad the tipoEntidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEntidad, or with status {@code 400 (Bad Request)} if the tipoEntidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-entidads")
    public ResponseEntity<TipoEntidad> createTipoEntidad(@Valid @RequestBody TipoEntidad tipoEntidad) throws URISyntaxException {
        log.debug("REST request to save TipoEntidad : {}", tipoEntidad);
        if (tipoEntidad.getId() != null) {
            throw new BadRequestAlertException("A new tipoEntidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEntidad result = tipoEntidadRepository.save(tipoEntidad);
        return ResponseEntity.created(new URI("/api/tipo-entidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-entidads} : Updates an existing tipoEntidad.
     *
     * @param tipoEntidad the tipoEntidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEntidad,
     * or with status {@code 400 (Bad Request)} if the tipoEntidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEntidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-entidads")
    public ResponseEntity<TipoEntidad> updateTipoEntidad(@Valid @RequestBody TipoEntidad tipoEntidad) throws URISyntaxException {
        log.debug("REST request to update TipoEntidad : {}", tipoEntidad);
        if (tipoEntidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEntidad result = tipoEntidadRepository.save(tipoEntidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoEntidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-entidads} : get all the tipoEntidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEntidads in body.
     */
    @GetMapping("/tipo-entidads")
    public List<TipoEntidad> getAllTipoEntidads() {
        log.debug("REST request to get all TipoEntidads");
        return tipoEntidadRepository.findAll();
    }

    /**
     * {@code GET  /tipo-entidads/:id} : get the "id" tipoEntidad.
     *
     * @param id the id of the tipoEntidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEntidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-entidads/{id}")
    public ResponseEntity<TipoEntidad> getTipoEntidad(@PathVariable Long id) {
        log.debug("REST request to get TipoEntidad : {}", id);
        Optional<TipoEntidad> tipoEntidad = tipoEntidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoEntidad);
    }

    /**
     * {@code DELETE  /tipo-entidads/:id} : delete the "id" tipoEntidad.
     *
     * @param id the id of the tipoEntidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-entidads/{id}")
    public ResponseEntity<Void> deleteTipoEntidad(@PathVariable Long id) {
        log.debug("REST request to delete TipoEntidad : {}", id);
        tipoEntidadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
