package backend.web.rest;

import backend.BackendApp;
import backend.domain.GesAlerta;
import backend.repository.GesAlertaRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GesAlertaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class GesAlertaResourceIT {

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ALERTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALERTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GesAlertaRepository gesAlertaRepository;

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

    private MockMvc restGesAlertaMockMvc;

    private GesAlerta gesAlerta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GesAlertaResource gesAlertaResource = new GesAlertaResource(gesAlertaRepository);
        this.restGesAlertaMockMvc = MockMvcBuilders.standaloneSetup(gesAlertaResource)
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
    public static GesAlerta createEntity(EntityManager em) {
        GesAlerta gesAlerta = new GesAlerta()
            .nota(DEFAULT_NOTA)
            .fechaAlerta(DEFAULT_FECHA_ALERTA)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return gesAlerta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GesAlerta createUpdatedEntity(EntityManager em) {
        GesAlerta gesAlerta = new GesAlerta()
            .nota(UPDATED_NOTA)
            .fechaAlerta(UPDATED_FECHA_ALERTA)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        return gesAlerta;
    }

    @BeforeEach
    public void initTest() {
        gesAlerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createGesAlerta() throws Exception {
        int databaseSizeBeforeCreate = gesAlertaRepository.findAll().size();

        // Create the GesAlerta
        restGesAlertaMockMvc.perform(post("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesAlerta)))
            .andExpect(status().isCreated());

        // Validate the GesAlerta in the database
        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeCreate + 1);
        GesAlerta testGesAlerta = gesAlertaList.get(gesAlertaList.size() - 1);
        assertThat(testGesAlerta.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testGesAlerta.getFechaAlerta()).isEqualTo(DEFAULT_FECHA_ALERTA);
        assertThat(testGesAlerta.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testGesAlerta.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void createGesAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gesAlertaRepository.findAll().size();

        // Create the GesAlerta with an existing ID
        gesAlerta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGesAlertaMockMvc.perform(post("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesAlerta)))
            .andExpect(status().isBadRequest());

        // Validate the GesAlerta in the database
        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gesAlertaRepository.findAll().size();
        // set the field null
        gesAlerta.setNota(null);

        // Create the GesAlerta, which fails.

        restGesAlertaMockMvc.perform(post("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesAlerta)))
            .andExpect(status().isBadRequest());

        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAlertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gesAlertaRepository.findAll().size();
        // set the field null
        gesAlerta.setFechaAlerta(null);

        // Create the GesAlerta, which fails.

        restGesAlertaMockMvc.perform(post("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesAlerta)))
            .andExpect(status().isBadRequest());

        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGesAlertas() throws Exception {
        // Initialize the database
        gesAlertaRepository.saveAndFlush(gesAlerta);

        // Get all the gesAlertaList
        restGesAlertaMockMvc.perform(get("/api/ges-alertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gesAlerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
            .andExpect(jsonPath("$.[*].fechaAlerta").value(hasItem(DEFAULT_FECHA_ALERTA.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getGesAlerta() throws Exception {
        // Initialize the database
        gesAlertaRepository.saveAndFlush(gesAlerta);

        // Get the gesAlerta
        restGesAlertaMockMvc.perform(get("/api/ges-alertas/{id}", gesAlerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gesAlerta.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
            .andExpect(jsonPath("$.fechaAlerta").value(DEFAULT_FECHA_ALERTA.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGesAlerta() throws Exception {
        // Get the gesAlerta
        restGesAlertaMockMvc.perform(get("/api/ges-alertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGesAlerta() throws Exception {
        // Initialize the database
        gesAlertaRepository.saveAndFlush(gesAlerta);

        int databaseSizeBeforeUpdate = gesAlertaRepository.findAll().size();

        // Update the gesAlerta
        GesAlerta updatedGesAlerta = gesAlertaRepository.findById(gesAlerta.getId()).get();
        // Disconnect from session so that the updates on updatedGesAlerta are not directly saved in db
        em.detach(updatedGesAlerta);
        updatedGesAlerta
            .nota(UPDATED_NOTA)
            .fechaAlerta(UPDATED_FECHA_ALERTA)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restGesAlertaMockMvc.perform(put("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGesAlerta)))
            .andExpect(status().isOk());

        // Validate the GesAlerta in the database
        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeUpdate);
        GesAlerta testGesAlerta = gesAlertaList.get(gesAlertaList.size() - 1);
        assertThat(testGesAlerta.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testGesAlerta.getFechaAlerta()).isEqualTo(UPDATED_FECHA_ALERTA);
        assertThat(testGesAlerta.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testGesAlerta.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingGesAlerta() throws Exception {
        int databaseSizeBeforeUpdate = gesAlertaRepository.findAll().size();

        // Create the GesAlerta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGesAlertaMockMvc.perform(put("/api/ges-alertas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesAlerta)))
            .andExpect(status().isBadRequest());

        // Validate the GesAlerta in the database
        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGesAlerta() throws Exception {
        // Initialize the database
        gesAlertaRepository.saveAndFlush(gesAlerta);

        int databaseSizeBeforeDelete = gesAlertaRepository.findAll().size();

        // Delete the gesAlerta
        restGesAlertaMockMvc.perform(delete("/api/ges-alertas/{id}", gesAlerta.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GesAlerta> gesAlertaList = gesAlertaRepository.findAll();
        assertThat(gesAlertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
