package backend.web.rest;

import backend.domain.Dependencia;
import backend.repository.DependenciaRepository;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing {@link backend.domain.Dependencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DependenciaResource {

    private final Logger log = LoggerFactory.getLogger(DependenciaResource.class);

    private static final String ENTITY_NAME = "dependencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependenciaRepository dependenciaRepository;

    public DependenciaResource(DependenciaRepository dependenciaRepository) {
        this.dependenciaRepository = dependenciaRepository;
    }

    /**
     * {@code POST  /dependencias} : Create a new dependencia.
     *
     * @param dependencia the dependencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependencia, or with status {@code 400 (Bad Request)} if the dependencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dependencias")
    public ResponseEntity<Dependencia> createDependencia(@Valid @RequestBody Dependencia dependencia) throws URISyntaxException {
        log.debug("REST request to save Dependencia : {}", dependencia);
        if (dependencia.getId() != null) {
            throw new BadRequestAlertException("A new dependencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependencia result = dependenciaRepository.save(dependencia);
        return ResponseEntity.created(new URI("/api/dependencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dependencias} : Updates an existing dependencia.
     *
     * @param dependencia the dependencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependencia,
     * or with status {@code 400 (Bad Request)} if the dependencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dependencias")
    public ResponseEntity<Dependencia> updateDependencia(@Valid @RequestBody Dependencia dependencia) throws URISyntaxException {
        log.debug("REST request to update Dependencia : {}", dependencia);
        if (dependencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dependencia result = dependenciaRepository.save(dependencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dependencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dependencias} : get all the dependencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependencias in body.
     */
    @GetMapping("/dependencias")
    public List<Dependencia> getAllDependencias() {
        log.debug("REST request to get all Dependencias");
        return dependenciaRepository.findAll();
    }

    /**
     * {@code GET  /dependencias/:id} : get the "id" dependencia.
     *
     * @param id the id of the dependencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dependencias/{id}")
    public ResponseEntity<Dependencia> getDependencia(@PathVariable Long id) {
        log.debug("REST request to get Dependencia : {}", id);
        Optional<Dependencia> dependencia = dependenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dependencia);
    }

    /**
     * {@code DELETE  /dependencias/:id} : delete the "id" dependencia.
     *
     * @param id the id of the dependencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dependencias/{id}")
    public ResponseEntity<Void> deleteDependencia(@PathVariable Long id) {
        log.debug("REST request to delete Dependencia : {}", id);
        dependenciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
      /**
     * {@code GET  /dependencias/:idEntidad} : get the "ids" of entidades.
     *
     * @param id the id of the entidad to find a list of dependencias.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/buscaDependenciaPorEntidad/{idEntidad}")
    public List<Dependencia> buscaDependenciaPorEntidad(@PathVariable Long idEntidad) {
        log.debug("REST request to get comunas  por region : {}", idEntidad);
        return dependenciaRepository.buscaDependenciaPorEntidad(idEntidad);
    }

     /**
     * {@code GET  /buscaUsuariosDependencia/:idDependencia} : get the "ids" of usuarios.
     *
     * @param id the id of the dependencia to find a list of usuarios.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/buscaUsuariosDependencia/{idDependencia}")
    public String buscaUsuariosDependencia(@PathVariable Long idDependencia) throws JsonProcessingException{
        log.debug("REST request to get usuarios  por region : {}", idDependencia);
        String json = new ObjectMapper().writeValueAsString(dependenciaRepository.buscaUsuariosDependencia(idDependencia));
        return json;
    }
}
