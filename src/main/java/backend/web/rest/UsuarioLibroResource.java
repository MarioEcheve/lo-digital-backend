package backend.web.rest;

import backend.domain.UsuarioLibro;
import backend.repository.UsuarioLibroRepository;
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
 * REST controller for managing {@link backend.domain.UsuarioLibro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioLibroResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioLibroResource.class);

    private static final String ENTITY_NAME = "usuarioLibro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioLibroRepository usuarioLibroRepository;

    public UsuarioLibroResource(UsuarioLibroRepository usuarioLibroRepository) {
        this.usuarioLibroRepository = usuarioLibroRepository;
    }

    /**
     * {@code POST  /usuario-libros} : Create a new usuarioLibro.
     *
     * @param usuarioLibro the usuarioLibro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioLibro, or with status {@code 400 (Bad Request)} if the usuarioLibro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-libros")
    public ResponseEntity<UsuarioLibro> createUsuarioLibro(@RequestBody UsuarioLibro usuarioLibro) throws URISyntaxException {
        log.debug("REST request to save UsuarioLibro : {}", usuarioLibro);
        if (usuarioLibro.getId() != null) {
            throw new BadRequestAlertException("A new usuarioLibro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioLibro result = usuarioLibroRepository.save(usuarioLibro);
        return ResponseEntity.created(new URI("/api/usuario-libros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-libros} : Updates an existing usuarioLibro.
     *
     * @param usuarioLibro the usuarioLibro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioLibro,
     * or with status {@code 400 (Bad Request)} if the usuarioLibro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioLibro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-libros")
    public ResponseEntity<UsuarioLibro> updateUsuarioLibro(@RequestBody UsuarioLibro usuarioLibro) throws URISyntaxException {
        log.debug("REST request to update UsuarioLibro : {}", usuarioLibro);
        if (usuarioLibro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioLibro result = usuarioLibroRepository.save(usuarioLibro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioLibro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuario-libros} : get all the usuarioLibros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioLibros in body.
     */
    @GetMapping("/usuario-libros")
    public List<UsuarioLibro> getAllUsuarioLibros() {
        log.debug("REST request to get all UsuarioLibros");
        return usuarioLibroRepository.findAll();
    }

    /**
     * {@code GET  /usuario-libros/:id} : get the "id" usuarioLibro.
     *
     * @param id the id of the usuarioLibro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioLibro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-libros/{id}")
    public ResponseEntity<UsuarioLibro> getUsuarioLibro(@PathVariable Long id) {
        log.debug("REST request to get UsuarioLibro : {}", id);
        Optional<UsuarioLibro> usuarioLibro = usuarioLibroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuarioLibro);
    }

    /**
     * {@code DELETE  /usuario-libros/:id} : delete the "id" usuarioLibro.
     *
     * @param id the id of the usuarioLibro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-libros/{id}")
    public ResponseEntity<Void> deleteUsuarioLibro(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioLibro : {}", id);
        usuarioLibroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

     /**
     * {@code GET  /buscaDependenciaPorEntidad/:idEntidad} : get the "ids" of entidades.
     *
     * @param id the id of the entidad to find a list of dependencias.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/ListaUsuariosLibrosFolio/{idLibro}/{idUsuario}")
    public List<UsuarioLibro> ListaUsuariosLibrosFolio(@PathVariable Long idLibro,@PathVariable Long idUsuario) {
        log.debug("REST request to get usuario libro  por libro y usuario : {}", idLibro,idUsuario);
        return usuarioLibroRepository.ListaUsuariosLibrosFolio(idLibro,idUsuario);
    }

     /**
     * {@code GET  /ListaUsuariosLibros/:idlibro} : get the "ids" of usuarios libros .
     *
     * @param id the id of the libro to find a list of usuarios.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/ListaUsuariosLibros/{idLibro}/{idUsuario}")
    public List<UsuarioLibro> ListaUsuariosLibros(@PathVariable Long idLibro,@PathVariable Long idUsuario) {
        log.debug("REST request to get usuarios por libros  : {}", idLibro,idUsuario);
        return usuarioLibroRepository.ListaUsuariosLibros(idLibro,idUsuario);
    }
}
