package backend.web.rest;

import backend.domain.Libro;
import backend.repository.LibroRepository;
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
 * REST controller for managing {@link backend.domain.Libro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LibroResource {

    private final Logger log = LoggerFactory.getLogger(LibroResource.class);

    private static final String ENTITY_NAME = "libro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LibroRepository libroRepository;

    public LibroResource(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * {@code POST  /libros} : Create a new libro.
     *
     * @param libro the libro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new libro, or with status {@code 400 (Bad Request)} if the libro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/libros")
    public ResponseEntity<Libro> createLibro(@Valid @RequestBody Libro libro) throws URISyntaxException {
        log.debug("REST request to save Libro : {}", libro);
        if (libro.getId() != null) {
            throw new BadRequestAlertException("A new libro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Libro result = libroRepository.save(libro);
        return ResponseEntity.created(new URI("/api/libros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /libros} : Updates an existing libro.
     *
     * @param libro the libro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated libro,
     * or with status {@code 400 (Bad Request)} if the libro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the libro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/libros")
    public ResponseEntity<Libro> updateLibro(@Valid @RequestBody Libro libro) throws URISyntaxException {
        log.debug("REST request to update Libro : {}", libro);
        if (libro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Libro result = libroRepository.save(libro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, libro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /libros} : get all the libros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of libros in body.
     */
    @GetMapping("/libros")
    public List<Libro> getAllLibros() {
        log.debug("REST request to get all Libros");
        return libroRepository.findAll();
    }

    /**
     * {@code GET  /libros/:id} : get the "id" libro.
     *
     * @param id the id of the libro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the libro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long id) {
        log.debug("REST request to get Libro : {}", id);
        Optional<Libro> libro = libroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(libro);
    }

    /**
     * {@code DELETE  /libros/:id} : delete the "id" libro.
     *
     * @param id the id of the libro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        log.debug("REST request to delete Libro : {}", id);
        libroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /buscaDependenciaPorEntidad/:idEntidad} : get the "ids" of entidades.
     *
     * @param id the id of the entidad to find a list of dependencias.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/buscarlibroPorContrato/{idContrato}")
    public List<Libro> buscarlibroPorContrato(@PathVariable Long idContrato) {
        log.debug("REST request to get libros  por contrato : {}", idContrato);
        return libroRepository.buscarlibroPorContrato(idContrato);
    }
    /**
     * {@code GET  /getMisLibros/:idEntidad} : get the "ids" of usuario.
     *
     * @param id the id of the usuario to find a list of libros.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/getMisLibros/{idUsuario}")
    public List<Libro> getMisLibros(@PathVariable Long idUsuario) {
        log.debug("REST request to get libros  por usuario : {}", idUsuario);
        return libroRepository.getMisLibros(idUsuario);
    }
}
