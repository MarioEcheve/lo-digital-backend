package backend.web.rest;

import backend.domain.GesNota;
import backend.repository.GesNotaRepository;
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
 * REST controller for managing {@link backend.domain.GesNota}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GesNotaResource {

    private final Logger log = LoggerFactory.getLogger(GesNotaResource.class);

    private static final String ENTITY_NAME = "gesNota";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GesNotaRepository gesNotaRepository;

    public GesNotaResource(GesNotaRepository gesNotaRepository) {
        this.gesNotaRepository = gesNotaRepository;
    }

    /**
     * {@code POST  /ges-notas} : Create a new gesNota.
     *
     * @param gesNota the gesNota to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gesNota, or with status {@code 400 (Bad Request)} if the gesNota has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ges-notas")
    public ResponseEntity<GesNota> createGesNota(@Valid @RequestBody GesNota gesNota) throws URISyntaxException {
        log.debug("REST request to save GesNota : {}", gesNota);
        if (gesNota.getId() != null) {
            throw new BadRequestAlertException("A new gesNota cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GesNota result = gesNotaRepository.save(gesNota);
        return ResponseEntity.created(new URI("/api/ges-notas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ges-notas} : Updates an existing gesNota.
     *
     * @param gesNota the gesNota to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gesNota,
     * or with status {@code 400 (Bad Request)} if the gesNota is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gesNota couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ges-notas")
    public ResponseEntity<GesNota> updateGesNota(@Valid @RequestBody GesNota gesNota) throws URISyntaxException {
        log.debug("REST request to update GesNota : {}", gesNota);
        if (gesNota.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GesNota result = gesNotaRepository.save(gesNota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gesNota.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ges-notas} : get all the gesNotas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gesNotas in body.
     */
    @GetMapping("/ges-notas")
    public List<GesNota> getAllGesNotas() {
        log.debug("REST request to get all GesNotas");
        return gesNotaRepository.findAll();
    }

    /**
     * {@code GET  /ges-notas/:id} : get the "id" gesNota.
     *
     * @param id the id of the gesNota to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gesNota, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ges-notas/{id}")
    public ResponseEntity<GesNota> getGesNota(@PathVariable Long id) {
        log.debug("REST request to get GesNota : {}", id);
        Optional<GesNota> gesNota = gesNotaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gesNota);
    }

    /**
     * {@code DELETE  /ges-notas/:id} : delete the "id" gesNota.
     *
     * @param id the id of the gesNota to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ges-notas/{id}")
    public ResponseEntity<Void> deleteGesNota(@PathVariable Long id) {
        log.debug("REST request to delete GesNota : {}", id);
        gesNotaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
