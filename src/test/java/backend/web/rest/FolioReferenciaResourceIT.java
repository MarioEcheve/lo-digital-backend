package backend.web.rest;

import backend.BackendApp;
import backend.domain.FolioReferencia;
import backend.repository.FolioReferenciaRepository;
import backend.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FolioReferenciaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class FolioReferenciaResourceIT {

    private static final String DEFAULT_ASUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASUNTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_FOLIO_ORIGEN = 1;
    private static final Integer UPDATED_ID_FOLIO_ORIGEN = 2;

    private static final Integer DEFAULT_ID_FOLIO_REFERENCIA = 1;
    private static final Integer UPDATED_ID_FOLIO_REFERENCIA = 2;

    @Autowired
    private FolioReferenciaRepository folioReferenciaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFolioReferenciaMockMvc;

    private FolioReferencia folioReferencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FolioReferenciaResource folioReferenciaResource = new FolioReferenciaResource(folioReferenciaRepository);
        this.restFolioReferenciaMockMvc = MockMvcBuilders.standaloneSetup(folioReferenciaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FolioReferencia createEntity(EntityManager em) {
        FolioReferencia folioReferencia = new FolioReferencia()
            .asunto(DEFAULT_ASUNTO)
            .idFolioOrigen(DEFAULT_ID_FOLIO_ORIGEN)
            .idFolioReferencia(DEFAULT_ID_FOLIO_REFERENCIA);
        return folioReferencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FolioReferencia createUpdatedEntity(EntityManager em) {
        FolioReferencia folioReferencia = new FolioReferencia()
            .asunto(UPDATED_ASUNTO)
            .idFolioOrigen(UPDATED_ID_FOLIO_ORIGEN)
            .idFolioReferencia(UPDATED_ID_FOLIO_REFERENCIA);
        return folioReferencia;
    }

    @BeforeEach
    public void initTest() {
        folioReferencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createFolioReferencia() throws Exception {
        int databaseSizeBeforeCreate = folioReferenciaRepository.findAll().size();

        // Create the FolioReferencia
        restFolioReferenciaMockMvc.perform(post("/api/folio-referencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folioReferencia)))
            .andExpect(status().isCreated());

        // Validate the FolioReferencia in the database
        List<FolioReferencia> folioReferenciaList = folioReferenciaRepository.findAll();
        assertThat(folioReferenciaList).hasSize(databaseSizeBeforeCreate + 1);
        FolioReferencia testFolioReferencia = folioReferenciaList.get(folioReferenciaList.size() - 1);
        assertThat(testFolioReferencia.getAsunto()).isEqualTo(DEFAULT_ASUNTO);
        assertThat(testFolioReferencia.getIdFolioOrigen()).isEqualTo(DEFAULT_ID_FOLIO_ORIGEN);
        assertThat(testFolioReferencia.getIdFolioReferencia()).isEqualTo(DEFAULT_ID_FOLIO_REFERENCIA);
    }

    @Test
    @Transactional
    public void createFolioReferenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = folioReferenciaRepository.findAll().size();

        // Create the FolioReferencia with an existing ID
        folioReferencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFolioReferenciaMockMvc.perform(post("/api/folio-referencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folioReferencia)))
            .andExpect(status().isBadRequest());

        // Validate the FolioReferencia in the database
        List<FolioReferencia> folioReferenciaList = folioReferenciaRepository.findAll();
        assertThat(folioReferenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFolioReferencias() throws Exception {
        // Initialize the database
        folioReferenciaRepository.saveAndFlush(folioReferencia);

        // Get all the folioReferenciaList
        restFolioReferenciaMockMvc.perform(get("/api/folio-referencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(folioReferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].asunto").value(hasItem(DEFAULT_ASUNTO)))
            .andExpect(jsonPath("$.[*].idFolioOrigen").value(hasItem(DEFAULT_ID_FOLIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].idFolioReferencia").value(hasItem(DEFAULT_ID_FOLIO_REFERENCIA)));
    }
    
    @Test
    @Transactional
    public void getFolioReferencia() throws Exception {
        // Initialize the database
        folioReferenciaRepository.saveAndFlush(folioReferencia);

        // Get the folioReferencia
        restFolioReferenciaMockMvc.perform(get("/api/folio-referencias/{id}", folioReferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(folioReferencia.getId().intValue()))
            .andExpect(jsonPath("$.asunto").value(DEFAULT_ASUNTO))
            .andExpect(jsonPath("$.idFolioOrigen").value(DEFAULT_ID_FOLIO_ORIGEN))
            .andExpect(jsonPath("$.idFolioReferencia").value(DEFAULT_ID_FOLIO_REFERENCIA));
    }

    @Test
    @Transactional
    public void getNonExistingFolioReferencia() throws Exception {
        // Get the folioReferencia
        restFolioReferenciaMockMvc.perform(get("/api/folio-referencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFolioReferencia() throws Exception {
        // Initialize the database
        folioReferenciaRepository.saveAndFlush(folioReferencia);

        int databaseSizeBeforeUpdate = folioReferenciaRepository.findAll().size();

        // Update the folioReferencia
        FolioReferencia updatedFolioReferencia = folioReferenciaRepository.findById(folioReferencia.getId()).get();
        // Disconnect from session so that the updates on updatedFolioReferencia are not directly saved in db
        em.detach(updatedFolioReferencia);
        updatedFolioReferencia
            .asunto(UPDATED_ASUNTO)
            .idFolioOrigen(UPDATED_ID_FOLIO_ORIGEN)
            .idFolioReferencia(UPDATED_ID_FOLIO_REFERENCIA);

        restFolioReferenciaMockMvc.perform(put("/api/folio-referencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFolioReferencia)))
            .andExpect(status().isOk());

        // Validate the FolioReferencia in the database
        List<FolioReferencia> folioReferenciaList = folioReferenciaRepository.findAll();
        assertThat(folioReferenciaList).hasSize(databaseSizeBeforeUpdate);
        FolioReferencia testFolioReferencia = folioReferenciaList.get(folioReferenciaList.size() - 1);
        assertThat(testFolioReferencia.getAsunto()).isEqualTo(UPDATED_ASUNTO);
        assertThat(testFolioReferencia.getIdFolioOrigen()).isEqualTo(UPDATED_ID_FOLIO_ORIGEN);
        assertThat(testFolioReferencia.getIdFolioReferencia()).isEqualTo(UPDATED_ID_FOLIO_REFERENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingFolioReferencia() throws Exception {
        int databaseSizeBeforeUpdate = folioReferenciaRepository.findAll().size();

        // Create the FolioReferencia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFolioReferenciaMockMvc.perform(put("/api/folio-referencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folioReferencia)))
            .andExpect(status().isBadRequest());

        // Validate the FolioReferencia in the database
        List<FolioReferencia> folioReferenciaList = folioReferenciaRepository.findAll();
        assertThat(folioReferenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFolioReferencia() throws Exception {
        // Initialize the database
        folioReferenciaRepository.saveAndFlush(folioReferencia);

        int databaseSizeBeforeDelete = folioReferenciaRepository.findAll().size();

        // Delete the folioReferencia
        restFolioReferenciaMockMvc.perform(delete("/api/folio-referencias/{id}", folioReferencia.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FolioReferencia> folioReferenciaList = folioReferenciaRepository.findAll();
        assertThat(folioReferenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
