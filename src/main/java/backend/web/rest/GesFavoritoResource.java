package backend.web.rest;

import backend.domain.GesFavorito;
import backend.repository.GesFavoritoRepository;
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
 * REST controller for managing {@link backend.domain.GesFavorito}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GesFavoritoResource {

    private final Logger log = LoggerFactory.getLogger(GesFavoritoResource.class);

    private static final String ENTITY_NAME = "gesFavorito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GesFavoritoRepository gesFavoritoRepository;

    public GesFavoritoResource(GesFavoritoRepository gesFavoritoRepository) {
        this.gesFavoritoRepository = gesFavoritoRepository;
    }

    /**
     * {@code POST  /ges-favoritos} : Create a new gesFavorito.
     *
     * @param gesFavorito the gesFavorito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gesFavorito, or with status {@code 400 (Bad Request)} if the gesFavorito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ges-favoritos")
    public ResponseEntity<GesFavorito> createGesFavorito(@Valid @RequestBody GesFavorito gesFavorito) throws URISyntaxException {
        log.debug("REST request to save GesFavorito : {}", gesFavorito);
        if (gesFavorito.getId() != null) {
            throw new BadRequestAlertException("A new gesFavorito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GesFavorito result = gesFavoritoRepository.save(gesFavorito);
        return ResponseEntity.created(new URI("/api/ges-favoritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ges-favoritos} : Updates an existing gesFavorito.
     *
     * @param gesFavorito the gesFavorito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gesFavorito,
     * or with status {@code 400 (Bad Request)} if the gesFavorito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gesFavorito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ges-favoritos")
    public ResponseEntity<GesFavorito> updateGesFavorito(@Valid @RequestBody GesFavorito gesFavorito) throws URISyntaxException {
        log.debug("REST request to update GesFavorito : {}", gesFavorito);
        if (gesFavorito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GesFavorito result = gesFavoritoRepository.save(gesFavorito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gesFavorito.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ges-favoritos} : get all the gesFavoritos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gesFavoritos in body.
     */
    @GetMapping("/ges-favoritos")
    public List<GesFavorito> getAllGesFavoritos() {
        log.debug("REST request to get all GesFavoritos");
        return gesFavoritoRepository.findAll();
    }

    /**
     * {@code GET  /ges-favoritos/:id} : get the "id" gesFavorito.
     *
     * @param id the id of the gesFavorito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gesFavorito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ges-favoritos/{id}")
    public ResponseEntity<GesFavorito> getGesFavorito(@PathVariable Long id) {
        log.debug("REST request to get GesFavorito : {}", id);
        Optional<GesFavorito> gesFavorito = gesFavoritoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gesFavorito);
    }

    /**
     * {@code DELETE  /ges-favoritos/:id} : delete the "id" gesFavorito.
     *
     * @param id the id of the gesFavorito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ges-favoritos/{id}")
    public ResponseEntity<Void> deleteGesFavorito(@PathVariable Long id) {
        log.debug("REST request to delete GesFavorito : {}", id);
        gesFavoritoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
