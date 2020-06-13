package backend.web.rest;

import backend.BackendApp;
import backend.domain.TipoFolio;
import backend.repository.TipoFolioRepository;
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
 * Integration tests for the {@link TipoFolioResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class TipoFolioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISIBLE_MAESTRO = false;
    private static final Boolean UPDATED_VISIBLE_MAESTRO = true;

    private static final Boolean DEFAULT_VISIBLE_AUXLIAR = false;
    private static final Boolean UPDATED_VISIBLE_AUXLIAR = true;

    private static final Boolean DEFAULT_VISIBLE_MANDANTE = false;
    private static final Boolean UPDATED_VISIBLE_MANDANTE = true;

    private static final Boolean DEFAULT_VISIBLE_CONTRATISTA = false;
    private static final Boolean UPDATED_VISIBLE_CONTRATISTA = true;

    @Autowired
    private TipoFolioRepository tipoFolioRepository;

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

    private MockMvc restTipoFolioMockMvc;

    private TipoFolio tipoFolio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoFolioResource tipoFolioResource = new TipoFolioResource(tipoFolioRepository);
        this.restTipoFolioMockMvc = MockMvcBuilders.standaloneSetup(tipoFolioResource)
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
    public static TipoFolio createEntity(EntityManager em) {
        TipoFolio tipoFolio = new TipoFolio()
            .nombre(DEFAULT_NOMBRE)
            .visibleMaestro(DEFAULT_VISIBLE_MAESTRO)
            .visibleAuxliar(DEFAULT_VISIBLE_AUXLIAR)
            .visibleMandante(DEFAULT_VISIBLE_MANDANTE)
            .visibleContratista(DEFAULT_VISIBLE_CONTRATISTA);
        return tipoFolio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoFolio createUpdatedEntity(EntityManager em) {
        TipoFolio tipoFolio = new TipoFolio()
            .nombre(UPDATED_NOMBRE)
            .visibleMaestro(UPDATED_VISIBLE_MAESTRO)
            .visibleAuxliar(UPDATED_VISIBLE_AUXLIAR)
            .visibleMandante(UPDATED_VISIBLE_MANDANTE)
            .visibleContratista(UPDATED_VISIBLE_CONTRATISTA);
        return tipoFolio;
    }

    @BeforeEach
    public void initTest() {
        tipoFolio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoFolio() throws Exception {
        int databaseSizeBeforeCreate = tipoFolioRepository.findAll().size();

        // Create the TipoFolio
        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isCreated());

        // Validate the TipoFolio in the database
        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoFolio testTipoFolio = tipoFolioList.get(tipoFolioList.size() - 1);
        assertThat(testTipoFolio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoFolio.isVisibleMaestro()).isEqualTo(DEFAULT_VISIBLE_MAESTRO);
        assertThat(testTipoFolio.isVisibleAuxliar()).isEqualTo(DEFAULT_VISIBLE_AUXLIAR);
        assertThat(testTipoFolio.isVisibleMandante()).isEqualTo(DEFAULT_VISIBLE_MANDANTE);
        assertThat(testTipoFolio.isVisibleContratista()).isEqualTo(DEFAULT_VISIBLE_CONTRATISTA);
    }

    @Test
    @Transactional
    public void createTipoFolioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoFolioRepository.findAll().size();

        // Create the TipoFolio with an existing ID
        tipoFolio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFolio in the database
        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVisibleMaestroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFolioRepository.findAll().size();
        // set the field null
        tipoFolio.setVisibleMaestro(null);

        // Create the TipoFolio, which fails.

        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibleAuxliarIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFolioRepository.findAll().size();
        // set the field null
        tipoFolio.setVisibleAuxliar(null);

        // Create the TipoFolio, which fails.

        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibleMandanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFolioRepository.findAll().size();
        // set the field null
        tipoFolio.setVisibleMandante(null);

        // Create the TipoFolio, which fails.

        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibleContratistaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFolioRepository.findAll().size();
        // set the field null
        tipoFolio.setVisibleContratista(null);

        // Create the TipoFolio, which fails.

        restTipoFolioMockMvc.perform(post("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoFolios() throws Exception {
        // Initialize the database
        tipoFolioRepository.saveAndFlush(tipoFolio);

        // Get all the tipoFolioList
        restTipoFolioMockMvc.perform(get("/api/tipo-folios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoFolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].visibleMaestro").value(hasItem(DEFAULT_VISIBLE_MAESTRO.booleanValue())))
            .andExpect(jsonPath("$.[*].visibleAuxliar").value(hasItem(DEFAULT_VISIBLE_AUXLIAR.booleanValue())))
            .andExpect(jsonPath("$.[*].visibleMandante").value(hasItem(DEFAULT_VISIBLE_MANDANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].visibleContratista").value(hasItem(DEFAULT_VISIBLE_CONTRATISTA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoFolio() throws Exception {
        // Initialize the database
        tipoFolioRepository.saveAndFlush(tipoFolio);

        // Get the tipoFolio
        restTipoFolioMockMvc.perform(get("/api/tipo-folios/{id}", tipoFolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoFolio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.visibleMaestro").value(DEFAULT_VISIBLE_MAESTRO.booleanValue()))
            .andExpect(jsonPath("$.visibleAuxliar").value(DEFAULT_VISIBLE_AUXLIAR.booleanValue()))
            .andExpect(jsonPath("$.visibleMandante").value(DEFAULT_VISIBLE_MANDANTE.booleanValue()))
            .andExpect(jsonPath("$.visibleContratista").value(DEFAULT_VISIBLE_CONTRATISTA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoFolio() throws Exception {
        // Get the tipoFolio
        restTipoFolioMockMvc.perform(get("/api/tipo-folios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoFolio() throws Exception {
        // Initialize the database
        tipoFolioRepository.saveAndFlush(tipoFolio);

        int databaseSizeBeforeUpdate = tipoFolioRepository.findAll().size();

        // Update the tipoFolio
        TipoFolio updatedTipoFolio = tipoFolioRepository.findById(tipoFolio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoFolio are not directly saved in db
        em.detach(updatedTipoFolio);
        updatedTipoFolio
            .nombre(UPDATED_NOMBRE)
            .visibleMaestro(UPDATED_VISIBLE_MAESTRO)
            .visibleAuxliar(UPDATED_VISIBLE_AUXLIAR)
            .visibleMandante(UPDATED_VISIBLE_MANDANTE)
            .visibleContratista(UPDATED_VISIBLE_CONTRATISTA);

        restTipoFolioMockMvc.perform(put("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoFolio)))
            .andExpect(status().isOk());

        // Validate the TipoFolio in the database
        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeUpdate);
        TipoFolio testTipoFolio = tipoFolioList.get(tipoFolioList.size() - 1);
        assertThat(testTipoFolio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoFolio.isVisibleMaestro()).isEqualTo(UPDATED_VISIBLE_MAESTRO);
        assertThat(testTipoFolio.isVisibleAuxliar()).isEqualTo(UPDATED_VISIBLE_AUXLIAR);
        assertThat(testTipoFolio.isVisibleMandante()).isEqualTo(UPDATED_VISIBLE_MANDANTE);
        assertThat(testTipoFolio.isVisibleContratista()).isEqualTo(UPDATED_VISIBLE_CONTRATISTA);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoFolio() throws Exception {
        int databaseSizeBeforeUpdate = tipoFolioRepository.findAll().size();

        // Create the TipoFolio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoFolioMockMvc.perform(put("/api/tipo-folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFolio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFolio in the database
        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoFolio() throws Exception {
        // Initialize the database
        tipoFolioRepository.saveAndFlush(tipoFolio);

        int databaseSizeBeforeDelete = tipoFolioRepository.findAll().size();

        // Delete the tipoFolio
        restTipoFolioMockMvc.perform(delete("/api/tipo-folios/{id}", tipoFolio.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoFolio> tipoFolioList = tipoFolioRepository.findAll();
        assertThat(tipoFolioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
