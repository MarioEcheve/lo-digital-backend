package backend.web.rest;

import backend.domain.UsuarioDependencia;
import backend.repository.UsuarioDependenciaRepository;
import backend.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link backend.domain.UsuarioDependencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioDependenciaResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioDependenciaResource.class);

    private static final String ENTITY_NAME = "usuarioDependencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioDependenciaRepository usuarioDependenciaRepository;

    public UsuarioDependenciaResource(UsuarioDependenciaRepository usuarioDependenciaRepository) {
        this.usuarioDependenciaRepository = usuarioDependenciaRepository;
    }

    /**
     * {@code POST  /usuario-dependencias} : Create a new usuarioDependencia.
     *
     * @param usuarioDependencia the usuarioDependencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioDependencia, or with status {@code 400 (Bad Request)} if the usuarioDependencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-dependencias")
    public ResponseEntity<UsuarioDependencia> createUsuarioDependencia(@RequestBody UsuarioDependencia usuarioDependencia) throws URISyntaxException {
        log.debug("REST request to save UsuarioDependencia : {}", usuarioDependencia);
        if (usuarioDependencia.getId() != null) {
            throw new BadRequestAlertException("A new usuarioDependencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioDependencia result = usuarioDependenciaRepository.save(usuarioDependencia);
        return ResponseEntity.created(new URI("/api/usuario-dependencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-dependencias} : Updates an existing usuarioDependencia.
     *
     * @param usuarioDependencia the usuarioDependencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioDependencia,
     * or with status {@code 400 (Bad Request)} if the usuarioDependencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioDependencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-dependencias")
    public ResponseEntity<UsuarioDependencia> updateUsuarioDependencia(@RequestBody UsuarioDependencia usuarioDependencia) throws URISyntaxException {
        log.debug("REST request to update UsuarioDependencia : {}", usuarioDependencia);
        if (usuarioDependencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioDependencia result = usuarioDependenciaRepository.save(usuarioDependencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioDependencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-dependencias} : get all the usuarioDependencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioDependencias in body.
     */
    @GetMapping("/usuario-dependencias")
    public List<UsuarioDependencia> getAllUsuarioDependencias() {
        log.debug("REST request to get all UsuarioDependencias");
        return usuarioDependenciaRepository.findAll();
    }

    /**
     * {@code GET  /usuario-dependencias/:id} : get the "id" usuarioDependencia.
     *
     * @param id the id of the usuarioDependencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioDependencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-dependencias/{id}")
    public ResponseEntity<UsuarioDependencia> getUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to get UsuarioDependencia : {}", id);
        Optional<UsuarioDependencia> usuarioDependencia = usuarioDependenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuarioDependencia);
    }

    /**
     * {@code DELETE  /usuario-dependencias/:id} : delete the "id" usuarioDependencia.
     *
     * @param id the id of the usuarioDependencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-dependencias/{id}")
    public ResponseEntity<Void> deleteUsuarioDependencia(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioDependencia : {}", id);
        usuarioDependenciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/findUserByUsuarioDependencia/{idUsuario}")
    public List<UsuarioDependencia> findUserByUsuarioDependencia(@PathVariable Long idUsuario) {
        log.debug("REST request to get usuario dependencias  por usuario : {}", idUsuario);
        return usuarioDependenciaRepository.findUserByUsuarioDependencia(idUsuario);
    }

    @GetMapping("/findUserByUsuarioDependenciaRolUser/{idUsuario}")
    public String findUserByUsuarioDependenciaRolUser(@PathVariable Long idUsuario) throws JsonProcessingException{
        String json = new ObjectMapper().writeValueAsString(usuarioDependenciaRepository.findUserByUsuarioDependenciaRolUser(idUsuario));
        return json;
    }
    @GetMapping("/findContratosByDependencia/{idDependencia}")
    public String findContratosByDependencia(@PathVariable Long idDependencia) throws JsonProcessingException{
        String json = new ObjectMapper().writeValueAsString(usuarioDependenciaRepository.findContratosByDependencia(idDependencia));
        return json;
    }
}
