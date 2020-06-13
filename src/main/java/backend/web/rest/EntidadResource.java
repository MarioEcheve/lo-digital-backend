package backend.web.rest;

import backend.domain.Entidad;
import backend.repository.EntidadRepository;
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
 * REST controller for managing {@link backend.domain.Entidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntidadResource {

    private final Logger log = LoggerFactory.getLogger(EntidadResource.class);

    private static final String ENTITY_NAME = "entidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadRepository entidadRepository;

    public EntidadResource(EntidadRepository entidadRepository) {
        this.entidadRepository = entidadRepository;
    }

    /**
     * {@code POST  /entidads} : Create a new entidad.
     *
     * @param entidad the entidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidad, or with status {@code 400 (Bad Request)} if the entidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidads")
    public ResponseEntity<Entidad> createEntidad(@Valid @RequestBody Entidad entidad) throws URISyntaxException {
        log.debug("REST request to save Entidad : {}", entidad);
        if (entidad.getId() != null) {
            throw new BadRequestAlertException("A new entidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entidad result = entidadRepository.save(entidad);
        return ResponseEntity.created(new URI("/api/entidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidads} : Updates an existing entidad.
     *
     * @param entidad the entidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidad,
     * or with status {@code 400 (Bad Request)} if the entidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidads")
    public ResponseEntity<Entidad> updateEntidad(@Valid @RequestBody Entidad entidad) throws URISyntaxException {
        log.debug("REST request to update Entidad : {}", entidad);
        if (entidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entidad result = entidadRepository.save(entidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entidads} : get all the entidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidads in body.
     */
    @GetMapping("/entidads")
    public List<Entidad> getAllEntidads() {
        log.debug("REST request to get all Entidads");
        return entidadRepository.findAll();
    }

    /**
     * {@code GET  /entidads/:id} : get the "id" entidad.
     *
     * @param id the id of the entidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidads/{id}")
    public ResponseEntity<Entidad> getEntidad(@PathVariable Long id) {
        log.debug("REST request to get Entidad : {}", id);
        Optional<Entidad> entidad = entidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entidad);
    }

    /**
     * {@code DELETE  /entidads/:id} : delete the "id" entidad.
     *
     * @param id the id of the entidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidads/{id}")
    public ResponseEntity<Void> deleteEntidad(@PathVariable Long id) {
        log.debug("REST request to delete Entidad : {}", id);
        entidadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
