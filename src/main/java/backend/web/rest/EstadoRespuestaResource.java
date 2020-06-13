package backend.web.rest;

import backend.domain.EstadoRespuesta;
import backend.repository.EstadoRespuestaRepository;
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
 * REST controller for managing {@link backend.domain.EstadoRespuesta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoRespuestaResource {

    private final Logger log = LoggerFactory.getLogger(EstadoRespuestaResource.class);

    private static final String ENTITY_NAME = "estadoRespuesta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoRespuestaRepository estadoRespuestaRepository;

    public EstadoRespuestaResource(EstadoRespuestaRepository estadoRespuestaRepository) {
        this.estadoRespuestaRepository = estadoRespuestaRepository;
    }

    /**
     * {@code POST  /estado-respuestas} : Create a new estadoRespuesta.
     *
     * @param estadoRespuesta the estadoRespuesta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoRespuesta, or with status {@code 400 (Bad Request)} if the estadoRespuesta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-respuestas")
    public ResponseEntity<EstadoRespuesta> createEstadoRespuesta(@RequestBody EstadoRespuesta estadoRespuesta) throws URISyntaxException {
        log.debug("REST request to save EstadoRespuesta : {}", estadoRespuesta);
        if (estadoRespuesta.getId() != null) {
            throw new BadRequestAlertException("A new estadoRespuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoRespuesta result = estadoRespuestaRepository.save(estadoRespuesta);
        return ResponseEntity.created(new URI("/api/estado-respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-respuestas} : Updates an existing estadoRespuesta.
     *
     * @param estadoRespuesta the estadoRespuesta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoRespuesta,
     * or with status {@code 400 (Bad Request)} if the estadoRespuesta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoRespuesta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-respuestas")
    public ResponseEntity<EstadoRespuesta> updateEstadoRespuesta(@RequestBody EstadoRespuesta estadoRespuesta) throws URISyntaxException {
        log.debug("REST request to update EstadoRespuesta : {}", estadoRespuesta);
        if (estadoRespuesta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoRespuesta result = estadoRespuestaRepository.save(estadoRespuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoRespuesta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-respuestas} : get all the estadoRespuestas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoRespuestas in body.
     */
    @GetMapping("/estado-respuestas")
    public List<EstadoRespuesta> getAllEstadoRespuestas() {
        log.debug("REST request to get all EstadoRespuestas");
        return estadoRespuestaRepository.findAll();
    }

    /**
     * {@code GET  /estado-respuestas/:id} : get the "id" estadoRespuesta.
     *
     * @param id the id of the estadoRespuesta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoRespuesta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-respuestas/{id}")
    public ResponseEntity<EstadoRespuesta> getEstadoRespuesta(@PathVariable Long id) {
        log.debug("REST request to get EstadoRespuesta : {}", id);
        Optional<EstadoRespuesta> estadoRespuesta = estadoRespuestaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoRespuesta);
    }

    /**
     * {@code DELETE  /estado-respuestas/:id} : delete the "id" estadoRespuesta.
     *
     * @param id the id of the estadoRespuesta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-respuestas/{id}")
    public ResponseEntity<Void> deleteEstadoRespuesta(@PathVariable Long id) {
        log.debug("REST request to delete EstadoRespuesta : {}", id);
        estadoRespuestaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
