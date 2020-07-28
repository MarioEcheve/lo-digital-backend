package backend.web.rest;

import backend.domain.FolioReferencia;
import backend.repository.FolioReferenciaRepository;
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
 * REST controller for managing {@link backend.domain.FolioReferencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FolioReferenciaResource {

    private final Logger log = LoggerFactory.getLogger(FolioReferenciaResource.class);

    private static final String ENTITY_NAME = "folioReferencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FolioReferenciaRepository folioReferenciaRepository;

    public FolioReferenciaResource(FolioReferenciaRepository folioReferenciaRepository) {
        this.folioReferenciaRepository = folioReferenciaRepository;
    }

    /**
     * {@code POST  /folio-referencias} : Create a new folioReferencia.
     *
     * @param folioReferencia the folioReferencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new folioReferencia, or with status {@code 400 (Bad Request)} if the folioReferencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/folio-referencias")
    public ResponseEntity<FolioReferencia> createFolioReferencia(@RequestBody FolioReferencia folioReferencia) throws URISyntaxException {
        log.debug("REST request to save FolioReferencia : {}", folioReferencia);
        if (folioReferencia.getId() != null) {
            throw new BadRequestAlertException("A new folioReferencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FolioReferencia result = folioReferenciaRepository.save(folioReferencia);
        return ResponseEntity.created(new URI("/api/folio-referencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /folio-referencias} : Updates an existing folioReferencia.
     *
     * @param folioReferencia the folioReferencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated folioReferencia,
     * or with status {@code 400 (Bad Request)} if the folioReferencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the folioReferencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/folio-referencias")
    public ResponseEntity<FolioReferencia> updateFolioReferencia(@RequestBody FolioReferencia folioReferencia) throws URISyntaxException {
        log.debug("REST request to update FolioReferencia : {}", folioReferencia);
        if (folioReferencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FolioReferencia result = folioReferenciaRepository.save(folioReferencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, folioReferencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /folio-referencias} : get all the folioReferencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of folioReferencias in body.
     */
    @GetMapping("/folio-referencias")
    public List<FolioReferencia> getAllFolioReferencias() {
        log.debug("REST request to get all FolioReferencias");
        return folioReferenciaRepository.findAll();
    }

    /**
     * {@code GET  /folio-referencias/:id} : get the "id" folioReferencia.
     *
     * @param id the id of the folioReferencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the folioReferencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/folio-referencias/{id}")
    public ResponseEntity<FolioReferencia> getFolioReferencia(@PathVariable Long id) {
        log.debug("REST request to get FolioReferencia : {}", id);
        Optional<FolioReferencia> folioReferencia = folioReferenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(folioReferencia);
    }

    /**
     * {@code DELETE  /folio-referencias/:id} : delete the "id" folioReferencia.
     *
     * @param id the id of the folioReferencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/folio-referencias/{id}")
    public ResponseEntity<Void> deleteFolioReferencia(@PathVariable Long id) {
        log.debug("REST request to delete FolioReferencia : {}", id);
        folioReferenciaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/folioReferenciaPorFolioOrigen/{id}")
    public List<FolioReferencia> buscaFoliosPorFolioOrigen(@PathVariable Long id) {
        log.debug("REST request to get folios referencias  por folios : {}", id);
        return folioReferenciaRepository.buscaFoliosPorFolioOrigen(id);
    }
}
