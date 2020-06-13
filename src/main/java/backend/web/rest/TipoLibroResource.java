package backend.web.rest;

import backend.domain.TipoLibro;
import backend.repository.TipoLibroRepository;
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
 * REST controller for managing {@link backend.domain.TipoLibro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoLibroResource {

    private final Logger log = LoggerFactory.getLogger(TipoLibroResource.class);

    private static final String ENTITY_NAME = "tipoLibro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoLibroRepository tipoLibroRepository;

    public TipoLibroResource(TipoLibroRepository tipoLibroRepository) {
        this.tipoLibroRepository = tipoLibroRepository;
    }

    /**
     * {@code POST  /tipo-libros} : Create a new tipoLibro.
     *
     * @param tipoLibro the tipoLibro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoLibro, or with status {@code 400 (Bad Request)} if the tipoLibro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-libros")
    public ResponseEntity<TipoLibro> createTipoLibro(@RequestBody TipoLibro tipoLibro) throws URISyntaxException {
        log.debug("REST request to save TipoLibro : {}", tipoLibro);
        if (tipoLibro.getId() != null) {
            throw new BadRequestAlertException("A new tipoLibro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoLibro result = tipoLibroRepository.save(tipoLibro);
        return ResponseEntity.created(new URI("/api/tipo-libros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-libros} : Updates an existing tipoLibro.
     *
     * @param tipoLibro the tipoLibro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoLibro,
     * or with status {@code 400 (Bad Request)} if the tipoLibro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoLibro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-libros")
    public ResponseEntity<TipoLibro> updateTipoLibro(@RequestBody TipoLibro tipoLibro) throws URISyntaxException {
        log.debug("REST request to update TipoLibro : {}", tipoLibro);
        if (tipoLibro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoLibro result = tipoLibroRepository.save(tipoLibro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoLibro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-libros} : get all the tipoLibros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoLibros in body.
     */
    @GetMapping("/tipo-libros")
    public List<TipoLibro> getAllTipoLibros() {
        log.debug("REST request to get all TipoLibros");
        return tipoLibroRepository.findAll();
    }

    /**
     * {@code GET  /tipo-libros/:id} : get the "id" tipoLibro.
     *
     * @param id the id of the tipoLibro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoLibro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-libros/{id}")
    public ResponseEntity<TipoLibro> getTipoLibro(@PathVariable Long id) {
        log.debug("REST request to get TipoLibro : {}", id);
        Optional<TipoLibro> tipoLibro = tipoLibroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoLibro);
    }

    /**
     * {@code DELETE  /tipo-libros/:id} : delete the "id" tipoLibro.
     *
     * @param id the id of the tipoLibro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-libros/{id}")
    public ResponseEntity<Void> deleteTipoLibro(@PathVariable Long id) {
        log.debug("REST request to delete TipoLibro : {}", id);
        tipoLibroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
