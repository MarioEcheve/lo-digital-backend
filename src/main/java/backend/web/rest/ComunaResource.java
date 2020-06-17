package backend.web.rest;

import backend.domain.Comuna;
import backend.repository.ComunaRepository;
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
import backend.domain.Comuna;

/**
 * REST controller for managing {@link backend.domain.Comuna}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComunaResource {

    private final Logger log = LoggerFactory.getLogger(ComunaResource.class);

    private static final String ENTITY_NAME = "comuna";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunaRepository comunaRepository;

    public ComunaResource(ComunaRepository comunaRepository) {
        this.comunaRepository = comunaRepository;
    }

    /**
     * {@code POST  /comunas} : Create a new comuna.
     *
     * @param comuna the comuna to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comuna, or with status {@code 400 (Bad Request)} if the comuna has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunas")
    public ResponseEntity<Comuna> createComuna(@Valid @RequestBody Comuna comuna) throws URISyntaxException {
        log.debug("REST request to save Comuna : {}", comuna);
        if (comuna.getId() != null) {
            throw new BadRequestAlertException("A new comuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comuna result = comunaRepository.save(comuna);
        return ResponseEntity.created(new URI("/api/comunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunas} : Updates an existing comuna.
     *
     * @param comuna the comuna to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comuna,
     * or with status {@code 400 (Bad Request)} if the comuna is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comuna couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunas")
    public ResponseEntity<Comuna> updateComuna(@Valid @RequestBody Comuna comuna) throws URISyntaxException {
        log.debug("REST request to update Comuna : {}", comuna);
        if (comuna.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comuna result = comunaRepository.save(comuna);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, comuna.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comunas} : get all the comunas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunas in body.
     */
    @GetMapping("/comunas")
    public List<Comuna> getAllComunas() {
        log.debug("REST request to get all Comunas");
        return comunaRepository.findAll();
    }

    /**
     * {@code GET  /comunas/:id} : get the "id" comuna.
     *
     * @param id the id of the comuna to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comuna, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunas/{id}")
    public ResponseEntity<Comuna> getComuna(@PathVariable Long id) {
        log.debug("REST request to get Comuna : {}", id);
        Optional<Comuna> comuna = comunaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comuna);
    }

    /**
     * {@code DELETE  /comunas/:id} : delete the "id" comuna.
     *
     * @param id the id of the comuna to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunas/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        log.debug("REST request to delete Comuna : {}", id);
        comunaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
     /**
     * {@code DELETE  /comunas/:id} : delete the "id" comuna.
     *
     * @param id the id of the region to find a list of comunas.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/buscaComunaPorRegion/{id}")
    public List<Comuna> buscaComunaPorRegion(@PathVariable Long id) {
        log.debug("REST request to get comunas  por region : {}", id);
        return comunaRepository.buscaComunaPorRegion(id);
    }
}
