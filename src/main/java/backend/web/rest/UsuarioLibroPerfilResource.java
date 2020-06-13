package backend.web.rest;

import backend.domain.UsuarioLibroPerfil;
import backend.repository.UsuarioLibroPerfilRepository;
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
 * REST controller for managing {@link backend.domain.UsuarioLibroPerfil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioLibroPerfilResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioLibroPerfilResource.class);

    private static final String ENTITY_NAME = "usuarioLibroPerfil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioLibroPerfilRepository usuarioLibroPerfilRepository;

    public UsuarioLibroPerfilResource(UsuarioLibroPerfilRepository usuarioLibroPerfilRepository) {
        this.usuarioLibroPerfilRepository = usuarioLibroPerfilRepository;
    }

    /**
     * {@code POST  /usuario-libro-perfils} : Create a new usuarioLibroPerfil.
     *
     * @param usuarioLibroPerfil the usuarioLibroPerfil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioLibroPerfil, or with status {@code 400 (Bad Request)} if the usuarioLibroPerfil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-libro-perfils")
    public ResponseEntity<UsuarioLibroPerfil> createUsuarioLibroPerfil(@RequestBody UsuarioLibroPerfil usuarioLibroPerfil) throws URISyntaxException {
        log.debug("REST request to save UsuarioLibroPerfil : {}", usuarioLibroPerfil);
        if (usuarioLibroPerfil.getId() != null) {
            throw new BadRequestAlertException("A new usuarioLibroPerfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioLibroPerfil result = usuarioLibroPerfilRepository.save(usuarioLibroPerfil);
        return ResponseEntity.created(new URI("/api/usuario-libro-perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-libro-perfils} : Updates an existing usuarioLibroPerfil.
     *
     * @param usuarioLibroPerfil the usuarioLibroPerfil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioLibroPerfil,
     * or with status {@code 400 (Bad Request)} if the usuarioLibroPerfil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioLibroPerfil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-libro-perfils")
    public ResponseEntity<UsuarioLibroPerfil> updateUsuarioLibroPerfil(@RequestBody UsuarioLibroPerfil usuarioLibroPerfil) throws URISyntaxException {
        log.debug("REST request to update UsuarioLibroPerfil : {}", usuarioLibroPerfil);
        if (usuarioLibroPerfil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioLibroPerfil result = usuarioLibroPerfilRepository.save(usuarioLibroPerfil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioLibroPerfil.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-libro-perfils} : get all the usuarioLibroPerfils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioLibroPerfils in body.
     */
    @GetMapping("/usuario-libro-perfils")
    public List<UsuarioLibroPerfil> getAllUsuarioLibroPerfils() {
        log.debug("REST request to get all UsuarioLibroPerfils");
        return usuarioLibroPerfilRepository.findAll();
    }

    /**
     * {@code GET  /usuario-libro-perfils/:id} : get the "id" usuarioLibroPerfil.
     *
     * @param id the id of the usuarioLibroPerfil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioLibroPerfil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-libro-perfils/{id}")
    public ResponseEntity<UsuarioLibroPerfil> getUsuarioLibroPerfil(@PathVariable Long id) {
        log.debug("REST request to get UsuarioLibroPerfil : {}", id);
        Optional<UsuarioLibroPerfil> usuarioLibroPerfil = usuarioLibroPerfilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuarioLibroPerfil);
    }

    /**
     * {@code DELETE  /usuario-libro-perfils/:id} : delete the "id" usuarioLibroPerfil.
     *
     * @param id the id of the usuarioLibroPerfil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-libro-perfils/{id}")
    public ResponseEntity<Void> deleteUsuarioLibroPerfil(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioLibroPerfil : {}", id);
        usuarioLibroPerfilRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
