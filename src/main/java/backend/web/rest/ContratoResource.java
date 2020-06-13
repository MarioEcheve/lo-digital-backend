package backend.web.rest;

import backend.domain.Contrato;
import backend.repository.ContratoRepository;
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
 * REST controller for managing {@link backend.domain.Contrato}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContratoResource {

    private final Logger log = LoggerFactory.getLogger(ContratoResource.class);

    private static final String ENTITY_NAME = "contrato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratoRepository contratoRepository;

    public ContratoResource(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    /**
     * {@code POST  /contratoes} : Create a new contrato.
     *
     * @param contrato the contrato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contrato, or with status {@code 400 (Bad Request)} if the contrato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contratoes")
    public ResponseEntity<Contrato> createContrato(@Valid @RequestBody Contrato contrato) throws URISyntaxException {
        log.debug("REST request to save Contrato : {}", contrato);
        if (contrato.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contrato result = contratoRepository.save(contrato);
        return ResponseEntity.created(new URI("/api/contratoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contratoes} : Updates an existing contrato.
     *
     * @param contrato the contrato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contrato,
     * or with status {@code 400 (Bad Request)} if the contrato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contrato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contratoes")
    public ResponseEntity<Contrato> updateContrato(@Valid @RequestBody Contrato contrato) throws URISyntaxException {
        log.debug("REST request to update Contrato : {}", contrato);
        if (contrato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contrato result = contratoRepository.save(contrato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contrato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contratoes} : get all the contratoes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratoes in body.
     */
    @GetMapping("/contratoes")
    public List<Contrato> getAllContratoes() {
        log.debug("REST request to get all Contratoes");
        return contratoRepository.findAll();
    }

    /**
     * {@code GET  /contratoes/:id} : get the "id" contrato.
     *
     * @param id the id of the contrato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contrato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contratoes/{id}")
    public ResponseEntity<Contrato> getContrato(@PathVariable Long id) {
        log.debug("REST request to get Contrato : {}", id);
        Optional<Contrato> contrato = contratoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contrato);
    }

    /**
     * {@code DELETE  /contratoes/:id} : delete the "id" contrato.
     *
     * @param id the id of the contrato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contratoes/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        log.debug("REST request to delete Contrato : {}", id);
        contratoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
