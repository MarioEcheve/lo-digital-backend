package backend.web.rest;

import backend.domain.ActividadRubro;
import backend.repository.ActividadRubroRepository;
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
 * REST controller for managing {@link backend.domain.ActividadRubro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActividadRubroResource {

    private final Logger log = LoggerFactory.getLogger(ActividadRubroResource.class);

    private static final String ENTITY_NAME = "actividadRubro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActividadRubroRepository actividadRubroRepository;

    public ActividadRubroResource(ActividadRubroRepository actividadRubroRepository) {
        this.actividadRubroRepository = actividadRubroRepository;
    }

    /**
     * {@code POST  /actividad-rubros} : Create a new actividadRubro.
     *
     * @param actividadRubro the actividadRubro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actividadRubro, or with status {@code 400 (Bad Request)} if the actividadRubro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actividad-rubros")
    public ResponseEntity<ActividadRubro> createActividadRubro(@RequestBody ActividadRubro actividadRubro) throws URISyntaxException {
        log.debug("REST request to save ActividadRubro : {}", actividadRubro);
        if (actividadRubro.getId() != null) {
            throw new BadRequestAlertException("A new actividadRubro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActividadRubro result = actividadRubroRepository.save(actividadRubro);
        return ResponseEntity.created(new URI("/api/actividad-rubros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actividad-rubros} : Updates an existing actividadRubro.
     *
     * @param actividadRubro the actividadRubro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actividadRubro,
     * or with status {@code 400 (Bad Request)} if the actividadRubro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actividadRubro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actividad-rubros")
    public ResponseEntity<ActividadRubro> updateActividadRubro(@RequestBody ActividadRubro actividadRubro) throws URISyntaxException {
        log.debug("REST request to update ActividadRubro : {}", actividadRubro);
        if (actividadRubro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActividadRubro result = actividadRubroRepository.save(actividadRubro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, actividadRubro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /actividad-rubros} : get all the actividadRubros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actividadRubros in body.
     */
    @GetMapping("/actividad-rubros")
    public List<ActividadRubro> getAllActividadRubros() {
        log.debug("REST request to get all ActividadRubros");
        return actividadRubroRepository.findAll();
    }

    /**
     * {@code GET  /actividad-rubros/:id} : get the "id" actividadRubro.
     *
     * @param id the id of the actividadRubro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actividadRubro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actividad-rubros/{id}")
    public ResponseEntity<ActividadRubro> getActividadRubro(@PathVariable Long id) {
        log.debug("REST request to get ActividadRubro : {}", id);
        Optional<ActividadRubro> actividadRubro = actividadRubroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actividadRubro);
    }

    /**
     * {@code DELETE  /actividad-rubros/:id} : delete the "id" actividadRubro.
     *
     * @param id the id of the actividadRubro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actividad-rubros/{id}")
    public ResponseEntity<Void> deleteActividadRubro(@PathVariable Long id) {
        log.debug("REST request to delete ActividadRubro : {}", id);
        actividadRubroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
