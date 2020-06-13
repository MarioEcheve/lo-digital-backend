package backend.web.rest;

import backend.BackendApp;
import backend.domain.TipoUsuarioDependencia;
import backend.repository.TipoUsuarioDependenciaRepository;
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
 * Integration tests for the {@link TipoUsuarioDependenciaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class TipoUsuarioDependenciaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoUsuarioDependenciaRepository tipoUsuarioDependenciaRepository;

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

    private MockMvc restTipoUsuarioDependenciaMockMvc;

    private TipoUsuarioDependencia tipoUsuarioDependencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoUsuarioDependenciaResource tipoUsuarioDependenciaResource = new TipoUsuarioDependenciaResource(tipoUsuarioDependenciaRepository);
        this.restTipoUsuarioDependenciaMockMvc = MockMvcBuilders.standaloneSetup(tipoUsuarioDependenciaResource)
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
    public static TipoUsuarioDependencia createEntity(EntityManager em) {
        TipoUsuarioDependencia tipoUsuarioDependencia = new TipoUsuarioDependencia()
            .nombre(DEFAULT_NOMBRE);
        return tipoUsuarioDependencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoUsuarioDependencia createUpdatedEntity(EntityManager em) {
        TipoUsuarioDependencia tipoUsuarioDependencia = new TipoUsuarioDependencia()
            .nombre(UPDATED_NOMBRE);
        return tipoUsuarioDependencia;
    }

    @BeforeEach
    public void initTest() {
        tipoUsuarioDependencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoUsuarioDependencia() throws Exception {
        int databaseSizeBeforeCreate = tipoUsuarioDependenciaRepository.findAll().size();

        // Create the TipoUsuarioDependencia
        restTipoUsuarioDependenciaMockMvc.perform(post("/api/tipo-usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoUsuarioDependencia)))
            .andExpect(status().isCreated());

        // Validate the TipoUsuarioDependencia in the database
        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoUsuarioDependencia testTipoUsuarioDependencia = tipoUsuarioDependenciaList.get(tipoUsuarioDependenciaList.size() - 1);
        assertThat(testTipoUsuarioDependencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoUsuarioDependenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoUsuarioDependenciaRepository.findAll().size();

        // Create the TipoUsuarioDependencia with an existing ID
        tipoUsuarioDependencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoUsuarioDependenciaMockMvc.perform(post("/api/tipo-usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoUsuarioDependencia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUsuarioDependencia in the database
        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoUsuarioDependenciaRepository.findAll().size();
        // set the field null
        tipoUsuarioDependencia.setNombre(null);

        // Create the TipoUsuarioDependencia, which fails.

        restTipoUsuarioDependenciaMockMvc.perform(post("/api/tipo-usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoUsuarioDependencia)))
            .andExpect(status().isBadRequest());

        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoUsuarioDependencias() throws Exception {
        // Initialize the database
        tipoUsuarioDependenciaRepository.saveAndFlush(tipoUsuarioDependencia);

        // Get all the tipoUsuarioDependenciaList
        restTipoUsuarioDependenciaMockMvc.perform(get("/api/tipo-usuario-dependencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUsuarioDependencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getTipoUsuarioDependencia() throws Exception {
        // Initialize the database
        tipoUsuarioDependenciaRepository.saveAndFlush(tipoUsuarioDependencia);

        // Get the tipoUsuarioDependencia
        restTipoUsuarioDependenciaMockMvc.perform(get("/api/tipo-usuario-dependencias/{id}", tipoUsuarioDependencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoUsuarioDependencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingTipoUsuarioDependencia() throws Exception {
        // Get the tipoUsuarioDependencia
        restTipoUsuarioDependenciaMockMvc.perform(get("/api/tipo-usuario-dependencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoUsuarioDependencia() throws Exception {
        // Initialize the database
        tipoUsuarioDependenciaRepository.saveAndFlush(tipoUsuarioDependencia);

        int databaseSizeBeforeUpdate = tipoUsuarioDependenciaRepository.findAll().size();

        // Update the tipoUsuarioDependencia
        TipoUsuarioDependencia updatedTipoUsuarioDependencia = tipoUsuarioDependenciaRepository.findById(tipoUsuarioDependencia.getId()).get();
        // Disconnect from session so that the updates on updatedTipoUsuarioDependencia are not directly saved in db
        em.detach(updatedTipoUsuarioDependencia);
        updatedTipoUsuarioDependencia
            .nombre(UPDATED_NOMBRE);

        restTipoUsuarioDependenciaMockMvc.perform(put("/api/tipo-usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoUsuarioDependencia)))
            .andExpect(status().isOk());

        // Validate the TipoUsuarioDependencia in the database
        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeUpdate);
        TipoUsuarioDependencia testTipoUsuarioDependencia = tipoUsuarioDependenciaList.get(tipoUsuarioDependenciaList.size() - 1);
        assertThat(testTipoUsuarioDependencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoUsuarioDependencia() throws Exception {
        int databaseSizeBeforeUpdate = tipoUsuarioDependenciaRepository.findAll().size();

        // Create the TipoUsuarioDependencia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoUsuarioDependenciaMockMvc.perform(put("/api/tipo-usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoUsuarioDependencia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUsuarioDependencia in the database
        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoUsuarioDependencia() throws Exception {
        // Initialize the database
        tipoUsuarioDependenciaRepository.saveAndFlush(tipoUsuarioDependencia);

        int databaseSizeBeforeDelete = tipoUsuarioDependenciaRepository.findAll().size();

        // Delete the tipoUsuarioDependencia
        restTipoUsuarioDependenciaMockMvc.perform(delete("/api/tipo-usuario-dependencias/{id}", tipoUsuarioDependencia.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoUsuarioDependencia> tipoUsuarioDependenciaList = tipoUsuarioDependenciaRepository.findAll();
        assertThat(tipoUsuarioDependenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
