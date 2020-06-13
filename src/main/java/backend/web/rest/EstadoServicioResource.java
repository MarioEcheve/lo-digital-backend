package backend.web.rest;

import backend.domain.EstadoServicio;
import backend.repository.EstadoServicioRepository;
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
 * REST controller for managing {@link backend.domain.EstadoServicio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoServicioResource {

    private final Logger log = LoggerFactory.getLogger(EstadoServicioResource.class);

    private static final String ENTITY_NAME = "estadoServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoServicioRepository estadoServicioRepository;

    public EstadoServicioResource(EstadoServicioRepository estadoServicioRepository) {
        this.estadoServicioRepository = estadoServicioRepository;
    }

    /**
     * {@code POST  /estado-servicios} : Create a new estadoServicio.
     *
     * @param estadoServicio the estadoServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoServicio, or with status {@code 400 (Bad Request)} if the estadoServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-servicios")
    public ResponseEntity<EstadoServicio> createEstadoServicio(@RequestBody EstadoServicio estadoServicio) throws URISyntaxException {
        log.debug("REST request to save EstadoServicio : {}", estadoServicio);
        if (estadoServicio.getId() != null) {
            throw new BadRequestAlertException("A new estadoServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoServicio result = estadoServicioRepository.save(estadoServicio);
        return ResponseEntity.created(new URI("/api/estado-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-servicios} : Updates an existing estadoServicio.
     *
     * @param estadoServicio the estadoServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoServicio,
     * or with status {@code 400 (Bad Request)} if the estadoServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-servicios")
    public ResponseEntity<EstadoServicio> updateEstadoServicio(@RequestBody EstadoServicio estadoServicio) throws URISyntaxException {
        log.debug("REST request to update EstadoServicio : {}", estadoServicio);
        if (estadoServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoServicio result = estadoServicioRepository.save(estadoServicio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-servicios} : get all the estadoServicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoServicios in body.
     */
    @GetMapping("/estado-servicios")
    public List<EstadoServicio> getAllEstadoServicios() {
        log.debug("REST request to get all EstadoServicios");
        return estadoServicioRepository.findAll();
    }

    /**
     * {@code GET  /estado-servicios/:id} : get the "id" estadoServicio.
     *
     * @param id the id of the estadoServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-servicios/{id}")
    public ResponseEntity<EstadoServicio> getEstadoServicio(@PathVariable Long id) {
        log.debug("REST request to get EstadoServicio : {}", id);
        Optional<EstadoServicio> estadoServicio = estadoServicioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoServicio);
    }

    /**
     * {@code DELETE  /estado-servicios/:id} : delete the "id" estadoServicio.
     *
     * @param id the id of the estadoServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-servicios/{id}")
    public ResponseEntity<Void> deleteEstadoServicio(@PathVariable Long id) {
        log.debug("REST request to delete EstadoServicio : {}", id);
        estadoServicioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
