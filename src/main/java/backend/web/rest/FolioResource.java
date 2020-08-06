package backend.web.rest;

import backend.domain.Folio;
import backend.repository.FolioRepository;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing {@link backend.domain.Folio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FolioResource {

    private final Logger log = LoggerFactory.getLogger(FolioResource.class);

    private static final String ENTITY_NAME = "folio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FolioRepository folioRepository;

    public FolioResource(FolioRepository folioRepository) {
        this.folioRepository = folioRepository;
    }

    /**
     * {@code POST  /folios} : Create a new folio.
     *
     * @param folio the folio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new folio, or with status {@code 400 (Bad Request)} if the folio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/folios")
    public ResponseEntity<Folio> createFolio(@Valid @RequestBody Folio folio) throws URISyntaxException {
        log.debug("REST request to save Folio : {}", folio);
        if (folio.getId() != null) {
            throw new BadRequestAlertException("A new folio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Folio result = folioRepository.save(folio);
        return ResponseEntity.created(new URI("/api/folios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /folios} : Updates an existing folio.
     *
     * @param folio the folio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated folio,
     * or with status {@code 400 (Bad Request)} if the folio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the folio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/folios")
    public ResponseEntity<Folio> updateFolio(@Valid @RequestBody Folio folio) throws URISyntaxException {
        log.debug("REST request to update Folio : {}", folio);
        if (folio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Folio result = folioRepository.save(folio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, folio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /folios} : get all the folios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of folios in body.
     */
    @GetMapping("/folios")
    public List<Folio> getAllFolios() {
        log.debug("REST request to get all Folios");
        return folioRepository.findAll();
    }

    /**
     * {@code GET  /folios/:id} : get the "id" folio.
     *
     * @param id the id of the folio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the folio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/folios/{id}")
    public ResponseEntity<Folio> getFolio(@PathVariable Long id) {
        log.debug("REST request to get Folio : {}", id);
        Optional<Folio> folio = folioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(folio);
    }

    /**
     * {@code DELETE  /folios/:id} : delete the "id" folio.
     *
     * @param id the id of the folio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/folios/{id}")
    public ResponseEntity<Void> deleteFolio(@PathVariable Long id) {
        log.debug("REST request to delete Folio : {}", id);
        folioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    /**
     * {@code GET  /buscarFolioPorLibro/:idLibro} : get the "idLibro" libro.
     *
     * @param id the id of the libro to get.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
     @GetMapping("/buscarFolioPorLibro/{idLibro}")
    public List<Folio> buscarFolioPorLibro(@PathVariable Long idLibro) {
        log.debug("REST request to get folios  por Libro : {}", idLibro);
        return folioRepository.buscarFolioPorLibro(idLibro);
    }
    @GetMapping("/correlativoFolio/{idLibro}")
    public String correlativoFolio(@PathVariable Long idLibro) throws JsonProcessingException{
        log.debug("REST request to get correlativo por libro : {}", idLibro);
        String json = new ObjectMapper().writeValueAsString(folioRepository.correlativoFolio(idLibro));
        return json;
    }

    @GetMapping("/folioReferencias/{id}")
    public ResponseEntity<Folio> getFolioReferencias(@PathVariable Long id) {
        log.debug("REST request to get Folio Referencias : {}", id);
        Optional<Folio> folio = folioRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(folio);
    }
    @GetMapping("/filtroFolioPersonalizado/{idLibro}/{emisor}/{receptor}/{asunto}")
    public String filtroFolioPersonalizado(
                    @PathVariable Long idLibro,
                    @PathVariable(required = false) String emisor,
                    @PathVariable(required = false) String receptor,
                    @PathVariable(required = false) String asunto) throws JsonProcessingException{
        if( emisor.equals("null")){
            emisor = new String("");
            log.debug("entro al emisor");
        }
        if( receptor.equals("null")){
            receptor = new String("");
            log.debug("entro al receptor");
        }
        if( asunto.equals("null")){
            asunto = new String("");
            log.debug("entro al asunto");
        }
        String json = new ObjectMapper().writeValueAsString(folioRepository.filtroFolioPersonalizado(idLibro,
                                                                                                    emisor,
                                                                                                    receptor,
                                                                                                    asunto));
        return json;
    }
    

}
