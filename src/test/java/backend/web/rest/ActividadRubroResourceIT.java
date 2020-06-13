package backend.web.rest;

import backend.BackendApp;
import backend.domain.ActividadRubro;
import backend.repository.ActividadRubroRepository;
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
 * Integration tests for the {@link ActividadRubroResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class ActividadRubroResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ActividadRubroRepository actividadRubroRepository;

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

    private MockMvc restActividadRubroMockMvc;

    private ActividadRubro actividadRubro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActividadRubroResource actividadRubroResource = new ActividadRubroResource(actividadRubroRepository);
        this.restActividadRubroMockMvc = MockMvcBuilders.standaloneSetup(actividadRubroResource)
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
    public static ActividadRubro createEntity(EntityManager em) {
        ActividadRubro actividadRubro = new ActividadRubro()
            .nombre(DEFAULT_NOMBRE);
        return actividadRubro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActividadRubro createUpdatedEntity(EntityManager em) {
        ActividadRubro actividadRubro = new ActividadRubro()
            .nombre(UPDATED_NOMBRE);
        return actividadRubro;
    }

    @BeforeEach
    public void initTest() {
        actividadRubro = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividadRubro() throws Exception {
        int databaseSizeBeforeCreate = actividadRubroRepository.findAll().size();

        // Create the ActividadRubro
        restActividadRubroMockMvc.perform(post("/api/actividad-rubros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actividadRubro)))
            .andExpect(status().isCreated());

        // Validate the ActividadRubro in the database
        List<ActividadRubro> actividadRubroList = actividadRubroRepository.findAll();
        assertThat(actividadRubroList).hasSize(databaseSizeBeforeCreate + 1);
        ActividadRubro testActividadRubro = actividadRubroList.get(actividadRubroList.size() - 1);
        assertThat(testActividadRubro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createActividadRubroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividadRubroRepository.findAll().size();

        // Create the ActividadRubro with an existing ID
        actividadRubro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadRubroMockMvc.perform(post("/api/actividad-rubros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actividadRubro)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadRubro in the database
        List<ActividadRubro> actividadRubroList = actividadRubroRepository.findAll();
        assertThat(actividadRubroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActividadRubros() throws Exception {
        // Initialize the database
        actividadRubroRepository.saveAndFlush(actividadRubro);

        // Get all the actividadRubroList
        restActividadRubroMockMvc.perform(get("/api/actividad-rubros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividadRubro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getActividadRubro() throws Exception {
        // Initialize the database
        actividadRubroRepository.saveAndFlush(actividadRubro);

        // Get the actividadRubro
        restActividadRubroMockMvc.perform(get("/api/actividad-rubros/{id}", actividadRubro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actividadRubro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingActividadRubro() throws Exception {
        // Get the actividadRubro
        restActividadRubroMockMvc.perform(get("/api/actividad-rubros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividadRubro() throws Exception {
        // Initialize the database
        actividadRubroRepository.saveAndFlush(actividadRubro);

        int databaseSizeBeforeUpdate = actividadRubroRepository.findAll().size();

        // Update the actividadRubro
        ActividadRubro updatedActividadRubro = actividadRubroRepository.findById(actividadRubro.getId()).get();
        // Disconnect from session so that the updates on updatedActividadRubro are not directly saved in db
        em.detach(updatedActividadRubro);
        updatedActividadRubro
            .nombre(UPDATED_NOMBRE);

        restActividadRubroMockMvc.perform(put("/api/actividad-rubros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividadRubro)))
            .andExpect(status().isOk());

        // Validate the ActividadRubro in the database
        List<ActividadRubro> actividadRubroList = actividadRubroRepository.findAll();
        assertThat(actividadRubroList).hasSize(databaseSizeBeforeUpdate);
        ActividadRubro testActividadRubro = actividadRubroList.get(actividadRubroList.size() - 1);
        assertThat(testActividadRubro.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingActividadRubro() throws Exception {
        int databaseSizeBeforeUpdate = actividadRubroRepository.findAll().size();

        // Create the ActividadRubro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadRubroMockMvc.perform(put("/api/actividad-rubros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actividadRubro)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadRubro in the database
        List<ActividadRubro> actividadRubroList = actividadRubroRepository.findAll();
        assertThat(actividadRubroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActividadRubro() throws Exception {
        // Initialize the database
        actividadRubroRepository.saveAndFlush(actividadRubro);

        int databaseSizeBeforeDelete = actividadRubroRepository.findAll().size();

        // Delete the actividadRubro
        restActividadRubroMockMvc.perform(delete("/api/actividad-rubros/{id}", actividadRubro.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActividadRubro> actividadRubroList = actividadRubroRepository.findAll();
        assertThat(actividadRubroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
