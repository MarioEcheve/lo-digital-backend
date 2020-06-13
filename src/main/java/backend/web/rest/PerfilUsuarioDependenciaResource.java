package backend.web.rest;

import backend.domain.PerfilUsuarioDependencia;
import backend.repository.PerfilUsuarioDependenciaRepository;
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
 * REST controller for managing {@link backend.domain.PerfilUsuarioDependencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfilUsuarioDependenciaResource {

    private final Logger log = LoggerFactory.getLogger(PerfilUsuarioDependenciaResource.class);

    private static final String ENTITY_NAME = "perfilUsuarioDependencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilUsuarioDependenciaRepository perfilUsuarioDependenciaRepository;

    public PerfilUsuarioDependenciaResource(PerfilUsuarioDependenciaRepository perfilUsuarioDependenciaRepository) {
        this.perfilUsuarioDependenciaRepository = perfilUsuarioDependenciaRepository;
    }

    /**
     * {@code POST  /perfil-usuario-dependencias} : Create a new perfilUsuarioDependencia.
     *
     * @param perfilUsuarioDependencia the perfilUsuarioDependencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilUsuarioDependencia, or with status {@code 400 (Bad Request)} if the perfilUsuarioDependencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perfil-usuario-dependencias")
    public ResponseEntity<PerfilUsuarioDependencia> createPerfilUsuarioDependencia(@RequestBody PerfilUsuarioDependencia perfilUsuarioDependencia) throws URISyntaxException {
        log.debug("REST request to save PerfilUsuarioDependencia : {}", perfilUsuarioDependencia);
        if (perfilUsuarioDependencia.getId() != null) {
            throw new BadRequestAlertException("A new perfilUsuarioDependencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilUsuarioDependencia result = perfilUsuarioDependenciaRepository.save(perfilUsuarioDependencia);
        return ResponseEntity.created(new URI("/api/perfil-usuario-dependencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perfil-usuario-dependencias} : Updates an existing perfilUsuarioDependencia.
     *
     * @param perfilUsuarioDependencia the perfilUsuarioDependencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilUsuarioDependencia,
     * or with status {@code 400 (Bad Request)} if the perfilUsuarioDependencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilUsuarioDependencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perfil-usuario-dependencias")
    public ResponseEntity<PerfilUsuarioDependencia> updatePerfilUsuarioDependencia(@RequestBody PerfilUsuarioDependencia perfilUsuarioDependencia) throws URISyntaxException {
        log.debug("REST request to update PerfilUsuarioDependencia : {}", perfilUsuarioDependencia);
        if (perfilUsuarioDependencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfilUsuarioDependencia result = perfilUsuarioDependenciaRepository.save(perfilUsuarioDependencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, perfilUsuarioDependencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perfil-usuario-dependencias} : get all the perfilUsuarioDependencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilUsuarioDependencias in body.
     */
    @GetMapping("/perfil-usuario-dependencias")
    public List<PerfilUsuarioDependencia> getAllPerfilUsuarioDependencias() {
        log.debug("REST request to get all PerfilUsuarioDependencias");
        return perfilUsuarioDependenciaRepository.findAll();
    }

    /**
     * {@code GET  /perfil-usuario-dependencias/:id} : get the "id" perfilUsuarioDependencia.
     *
     * @param id the id of the perfilUsuarioDependencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilUsuarioDependencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perfil-usuario-dependencias/{id}")
    public ResponseEntity<PerfilUsuarioDependencia> getPerfilUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to get PerfilUsuarioDependencia : {}", id);
        Optional<PerfilUsuarioDependencia> perfilUsuarioDependencia = perfilUsuarioDependenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfilUsuarioDependencia);
    }

    /**
     * {@code DELETE  /perfil-usuario-dependencias/:id} : delete the "id" perfilUsuarioDependencia.
     *
     * @param id the id of the perfilUsuarioDependencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perfil-usuario-dependencias/{id}")
    public ResponseEntity<Void> deletePerfilUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to delete PerfilUsuarioDependencia : {}", id);
        perfilUsuarioDependenciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
