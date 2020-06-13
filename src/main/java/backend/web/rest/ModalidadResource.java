package backend.web.rest;

import backend.domain.Modalidad;
import backend.repository.ModalidadRepository;
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
 * REST controller for managing {@link backend.domain.Modalidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ModalidadResource {

    private final Logger log = LoggerFactory.getLogger(ModalidadResource.class);

    private static final String ENTITY_NAME = "modalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModalidadRepository modalidadRepository;

    public ModalidadResource(ModalidadRepository modalidadRepository) {
        this.modalidadRepository = modalidadRepository;
    }

    /**
     * {@code POST  /modalidads} : Create a new modalidad.
     *
     * @param modalidad the modalidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modalidad, or with status {@code 400 (Bad Request)} if the modalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modalidads")
    public ResponseEntity<Modalidad> createModalidad(@Valid @RequestBody Modalidad modalidad) throws URISyntaxException {
        log.debug("REST request to save Modalidad : {}", modalidad);
        if (modalidad.getId() != null) {
            throw new BadRequestAlertException("A new modalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Modalidad result = modalidadRepository.save(modalidad);
        return ResponseEntity.created(new URI("/api/modalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modalidads} : Updates an existing modalidad.
     *
     * @param modalidad the modalidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modalidad,
     * or with status {@code 400 (Bad Request)} if the modalidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modalidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modalidads")
    public ResponseEntity<Modalidad> updateModalidad(@Valid @RequestBody Modalidad modalidad) throws URISyntaxException {
        log.debug("REST request to update Modalidad : {}", modalidad);
        if (modalidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Modalidad result = modalidadRepository.save(modalidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modalidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modalidads} : get all the modalidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modalidads in body.
     */
    @GetMapping("/modalidads")
    public List<Modalidad> getAllModalidads() {
        log.debug("REST request to get all Modalidads");
        return modalidadRepository.findAll();
    }

    /**
     * {@code GET  /modalidads/:id} : get the "id" modalidad.
     *
     * @param id the id of the modalidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modalidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modalidads/{id}")
    public ResponseEntity<Modalidad> getModalidad(@PathVariable Long id) {
        log.debug("REST request to get Modalidad : {}", id);
        Optional<Modalidad> modalidad = modalidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modalidad);
    }

    /**
     * {@code DELETE  /modalidads/:id} : delete the "id" modalidad.
     *
     * @param id the id of the modalidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modalidads/{id}")
    public ResponseEntity<Void> deleteModalidad(@PathVariable Long id) {
        log.debug("REST request to delete Modalidad : {}", id);
        modalidadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
