package backend.web.rest;

import backend.domain.GesAlerta;
import backend.repository.GesAlertaRepository;
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
 * REST controller for managing {@link backend.domain.GesAlerta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GesAlertaResource {

    private final Logger log = LoggerFactory.getLogger(GesAlertaResource.class);

    private static final String ENTITY_NAME = "gesAlerta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GesAlertaRepository gesAlertaRepository;

    public GesAlertaResource(GesAlertaRepository gesAlertaRepository) {
        this.gesAlertaRepository = gesAlertaRepository;
    }

    /**
     * {@code POST  /ges-alertas} : Create a new gesAlerta.
     *
     * @param gesAlerta the gesAlerta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gesAlerta, or with status {@code 400 (Bad Request)} if the gesAlerta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ges-alertas")
    public ResponseEntity<GesAlerta> createGesAlerta(@Valid @RequestBody GesAlerta gesAlerta) throws URISyntaxException {
        log.debug("REST request to save GesAlerta : {}", gesAlerta);
        if (gesAlerta.getId() != null) {
            throw new BadRequestAlertException("A new gesAlerta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GesAlerta result = gesAlertaRepository.save(gesAlerta);
        return ResponseEntity.created(new URI("/api/ges-alertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ges-alertas} : Updates an existing gesAlerta.
     *
     * @param gesAlerta the gesAlerta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gesAlerta,
     * or with status {@code 400 (Bad Request)} if the gesAlerta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gesAlerta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ges-alertas")
    public ResponseEntity<GesAlerta> updateGesAlerta(@Valid @RequestBody GesAlerta gesAlerta) throws URISyntaxException {
        log.debug("REST request to update GesAlerta : {}", gesAlerta);
        if (gesAlerta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GesAlerta result = gesAlertaRepository.save(gesAlerta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gesAlerta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ges-alertas} : get all the gesAlertas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gesAlertas in body.
     */
    @GetMapping("/ges-alertas")
    public List<GesAlerta> getAllGesAlertas() {
        log.debug("REST request to get all GesAlertas");
        return gesAlertaRepository.findAll();
    }

    /**
     * {@code GET  /ges-alertas/:id} : get the "id" gesAlerta.
     *
     * @param id the id of the gesAlerta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gesAlerta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ges-alertas/{id}")
    public ResponseEntity<GesAlerta> getGesAlerta(@PathVariable Long id) {
        log.debug("REST request to get GesAlerta : {}", id);
        Optional<GesAlerta> gesAlerta = gesAlertaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gesAlerta);
    }

    /**
     * {@code DELETE  /ges-alertas/:id} : delete the "id" gesAlerta.
     *
     * @param id the id of the gesAlerta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ges-alertas/{id}")
    public ResponseEntity<Void> deleteGesAlerta(@PathVariable Long id) {
        log.debug("REST request to delete GesAlerta : {}", id);
        gesAlertaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
