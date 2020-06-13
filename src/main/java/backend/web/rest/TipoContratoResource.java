package backend.web.rest;

import backend.domain.TipoContrato;
import backend.repository.TipoContratoRepository;
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
 * REST controller for managing {@link backend.domain.TipoContrato}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoContratoResource {

    private final Logger log = LoggerFactory.getLogger(TipoContratoResource.class);

    private static final String ENTITY_NAME = "tipoContrato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoContratoRepository tipoContratoRepository;

    public TipoContratoResource(TipoContratoRepository tipoContratoRepository) {
        this.tipoContratoRepository = tipoContratoRepository;
    }

    /**
     * {@code POST  /tipo-contratoes} : Create a new tipoContrato.
     *
     * @param tipoContrato the tipoContrato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoContrato, or with status {@code 400 (Bad Request)} if the tipoContrato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-contratoes")
    public ResponseEntity<TipoContrato> createTipoContrato(@Valid @RequestBody TipoContrato tipoContrato) throws URISyntaxException {
        log.debug("REST request to save TipoContrato : {}", tipoContrato);
        if (tipoContrato.getId() != null) {
            throw new BadRequestAlertException("A new tipoContrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoContrato result = tipoContratoRepository.save(tipoContrato);
        return ResponseEntity.created(new URI("/api/tipo-contratoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-contratoes} : Updates an existing tipoContrato.
     *
     * @param tipoContrato the tipoContrato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoContrato,
     * or with status {@code 400 (Bad Request)} if the tipoContrato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoContrato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-contratoes")
    public ResponseEntity<TipoContrato> updateTipoContrato(@Valid @RequestBody TipoContrato tipoContrato) throws URISyntaxException {
        log.debug("REST request to update TipoContrato : {}", tipoContrato);
        if (tipoContrato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoContrato result = tipoContratoRepository.save(tipoContrato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoContrato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-contratoes} : get all the tipoContratoes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoContratoes in body.
     */
    @GetMapping("/tipo-contratoes")
    public List<TipoContrato> getAllTipoContratoes() {
        log.debug("REST request to get all TipoContratoes");
        return tipoContratoRepository.findAll();
    }

    /**
     * {@code GET  /tipo-contratoes/:id} : get the "id" tipoContrato.
     *
     * @param id the id of the tipoContrato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoContrato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-contratoes/{id}")
    public ResponseEntity<TipoContrato> getTipoContrato(@PathVariable Long id) {
        log.debug("REST request to get TipoContrato : {}", id);
        Optional<TipoContrato> tipoContrato = tipoContratoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoContrato);
    }

    /**
     * {@code DELETE  /tipo-contratoes/:id} : delete the "id" tipoContrato.
     *
     * @param id the id of the tipoContrato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-contratoes/{id}")
    public ResponseEntity<Void> deleteTipoContrato(@PathVariable Long id) {
        log.debug("REST request to delete TipoContrato : {}", id);
        tipoContratoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
