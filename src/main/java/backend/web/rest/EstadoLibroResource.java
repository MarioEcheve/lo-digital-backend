package backend.web.rest;

import backend.domain.EstadoLibro;
import backend.repository.EstadoLibroRepository;
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
 * REST controller for managing {@link backend.domain.EstadoLibro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoLibroResource {

    private final Logger log = LoggerFactory.getLogger(EstadoLibroResource.class);

    private static final String ENTITY_NAME = "estadoLibro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoLibroRepository estadoLibroRepository;

    public EstadoLibroResource(EstadoLibroRepository estadoLibroRepository) {
        this.estadoLibroRepository = estadoLibroRepository;
    }

    /**
     * {@code POST  /estado-libros} : Create a new estadoLibro.
     *
     * @param estadoLibro the estadoLibro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoLibro, or with status {@code 400 (Bad Request)} if the estadoLibro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-libros")
    public ResponseEntity<EstadoLibro> createEstadoLibro(@RequestBody EstadoLibro estadoLibro) throws URISyntaxException {
        log.debug("REST request to save EstadoLibro : {}", estadoLibro);
        if (estadoLibro.getId() != null) {
            throw new BadRequestAlertException("A new estadoLibro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoLibro result = estadoLibroRepository.save(estadoLibro);
        return ResponseEntity.created(new URI("/api/estado-libros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-libros} : Updates an existing estadoLibro.
     *
     * @param estadoLibro the estadoLibro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoLibro,
     * or with status {@code 400 (Bad Request)} if the estadoLibro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoLibro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-libros")
    public ResponseEntity<EstadoLibro> updateEstadoLibro(@RequestBody EstadoLibro estadoLibro) throws URISyntaxException {
        log.debug("REST request to update EstadoLibro : {}", estadoLibro);
        if (estadoLibro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoLibro result = estadoLibroRepository.save(estadoLibro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoLibro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-libros} : get all the estadoLibros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoLibros in body.
     */
    @GetMapping("/estado-libros")
    public List<EstadoLibro> getAllEstadoLibros() {
        log.debug("REST request to get all EstadoLibros");
        return estadoLibroRepository.findAll();
    }

    /**
     * {@code GET  /estado-libros/:id} : get the "id" estadoLibro.
     *
     * @param id the id of the estadoLibro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoLibro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-libros/{id}")
    public ResponseEntity<EstadoLibro> getEstadoLibro(@PathVariable Long id) {
        log.debug("REST request to get EstadoLibro : {}", id);
        Optional<EstadoLibro> estadoLibro = estadoLibroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoLibro);
    }

    /**
     * {@code DELETE  /estado-libros/:id} : delete the "id" estadoLibro.
     *
     * @param id the id of the estadoLibro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-libros/{id}")
    public ResponseEntity<Void> deleteEstadoLibro(@PathVariable Long id) {
        log.debug("REST request to delete EstadoLibro : {}", id);
        estadoLibroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
