package backend.web.rest;

import backend.domain.TipoUsuarioDependencia;
import backend.repository.TipoUsuarioDependenciaRepository;
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
 * REST controller for managing {@link backend.domain.TipoUsuarioDependencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoUsuarioDependenciaResource {

    private final Logger log = LoggerFactory.getLogger(TipoUsuarioDependenciaResource.class);

    private static final String ENTITY_NAME = "tipoUsuarioDependencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoUsuarioDependenciaRepository tipoUsuarioDependenciaRepository;

    public TipoUsuarioDependenciaResource(TipoUsuarioDependenciaRepository tipoUsuarioDependenciaRepository) {
        this.tipoUsuarioDependenciaRepository = tipoUsuarioDependenciaRepository;
    }

    /**
     * {@code POST  /tipo-usuario-dependencias} : Create a new tipoUsuarioDependencia.
     *
     * @param tipoUsuarioDependencia the tipoUsuarioDependencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoUsuarioDependencia, or with status {@code 400 (Bad Request)} if the tipoUsuarioDependencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-usuario-dependencias")
    public ResponseEntity<TipoUsuarioDependencia> createTipoUsuarioDependencia(@Valid @RequestBody TipoUsuarioDependencia tipoUsuarioDependencia) throws URISyntaxException {
        log.debug("REST request to save TipoUsuarioDependencia : {}", tipoUsuarioDependencia);
        if (tipoUsuarioDependencia.getId() != null) {
            throw new BadRequestAlertException("A new tipoUsuarioDependencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoUsuarioDependencia result = tipoUsuarioDependenciaRepository.save(tipoUsuarioDependencia);
        return ResponseEntity.created(new URI("/api/tipo-usuario-dependencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-usuario-dependencias} : Updates an existing tipoUsuarioDependencia.
     *
     * @param tipoUsuarioDependencia the tipoUsuarioDependencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoUsuarioDependencia,
     * or with status {@code 400 (Bad Request)} if the tipoUsuarioDependencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoUsuarioDependencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-usuario-dependencias")
    public ResponseEntity<TipoUsuarioDependencia> updateTipoUsuarioDependencia(@Valid @RequestBody TipoUsuarioDependencia tipoUsuarioDependencia) throws URISyntaxException {
        log.debug("REST request to update TipoUsuarioDependencia : {}", tipoUsuarioDependencia);
        if (tipoUsuarioDependencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoUsuarioDependencia result = tipoUsuarioDependenciaRepository.save(tipoUsuarioDependencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoUsuarioDependencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-usuario-dependencias} : get all the tipoUsuarioDependencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoUsuarioDependencias in body.
     */
    @GetMapping("/tipo-usuario-dependencias")
    public List<TipoUsuarioDependencia> getAllTipoUsuarioDependencias() {
        log.debug("REST request to get all TipoUsuarioDependencias");
        return tipoUsuarioDependenciaRepository.findAll();
    }

    /**
     * {@code GET  /tipo-usuario-dependencias/:id} : get the "id" tipoUsuarioDependencia.
     *
     * @param id the id of the tipoUsuarioDependencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoUsuarioDependencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-usuario-dependencias/{id}")
    public ResponseEntity<TipoUsuarioDependencia> getTipoUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to get TipoUsuarioDependencia : {}", id);
        Optional<TipoUsuarioDependencia> tipoUsuarioDependencia = tipoUsuarioDependenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoUsuarioDependencia);
    }

    /**
     * {@code DELETE  /tipo-usuario-dependencias/:id} : delete the "id" tipoUsuarioDependencia.
     *
     * @param id the id of the tipoUsuarioDependencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-usuario-dependencias/{id}")
    public ResponseEntity<Void> deleteTipoUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to delete TipoUsuarioDependencia : {}", id);
        tipoUsuarioDependenciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
