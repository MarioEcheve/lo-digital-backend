package backend.web.rest;

import backend.domain.Archivo;
import backend.repository.ArchivoRepository;
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
 * REST controller for managing {@link backend.domain.Archivo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArchivoResource {

    private final Logger log = LoggerFactory.getLogger(ArchivoResource.class);

    private static final String ENTITY_NAME = "archivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchivoRepository archivoRepository;

    public ArchivoResource(ArchivoRepository archivoRepository) {
        this.archivoRepository = archivoRepository;
    }

    /**
     * {@code POST  /archivos} : Create a new archivo.
     *
     * @param archivo the archivo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archivo, or with status {@code 400 (Bad Request)} if the archivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/archivos")
    public ResponseEntity<Archivo> createArchivo(@Valid @RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to save Archivo : {}", archivo);
        if (archivo.getId() != null) {
            throw new BadRequestAlertException("A new archivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Archivo result = archivoRepository.save(archivo);
        return ResponseEntity.created(new URI("/api/archivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /archivos} : Updates an existing archivo.
     *
     * @param archivo the archivo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archivo,
     * or with status {@code 400 (Bad Request)} if the archivo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archivo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/archivos")
    public ResponseEntity<Archivo> updateArchivo(@Valid @RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to update Archivo : {}", archivo);
        if (archivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Archivo result = archivoRepository.save(archivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archivo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /archivos} : get all the archivos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archivos in body.
     */
    @GetMapping("/archivos")
    public List<Archivo> getAllArchivos() {
        log.debug("REST request to get all Archivos");
        return archivoRepository.findAll();
    }

    /**
     * {@code GET  /archivos/:id} : get the "id" archivo.
     *
     * @param id the id of the archivo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archivo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/archivos/{id}")
    public ResponseEntity<Archivo> getArchivo(@PathVariable Long id) {
        log.debug("REST request to get Archivo : {}", id);
        Optional<Archivo> archivo = archivoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(archivo);
    }

    /**
     * {@code DELETE  /archivos/:id} : delete the "id" archivo.
     *
     * @param id the id of the archivo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/archivos/{id}")
    public ResponseEntity<Void> deleteArchivo(@PathVariable Long id) {
        log.debug("REST request to delete Archivo : {}", id);
        archivoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
