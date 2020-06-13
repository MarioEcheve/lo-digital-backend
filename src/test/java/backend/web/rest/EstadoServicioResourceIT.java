package backend.web.rest;

import backend.BackendApp;
import backend.domain.EstadoServicio;
import backend.repository.EstadoServicioRepository;
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
 * Integration tests for the {@link EstadoServicioResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class EstadoServicioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EstadoServicioRepository estadoServicioRepository;

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

    private MockMvc restEstadoServicioMockMvc;

    private EstadoServicio estadoServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoServicioResource estadoServicioResource = new EstadoServicioResource(estadoServicioRepository);
        this.restEstadoServicioMockMvc = MockMvcBuilders.standaloneSetup(estadoServicioResource)
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
    public static EstadoServicio createEntity(EntityManager em) {
        EstadoServicio estadoServicio = new EstadoServicio()
            .nombre(DEFAULT_NOMBRE);
        return estadoServicio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoServicio createUpdatedEntity(EntityManager em) {
        EstadoServicio estadoServicio = new EstadoServicio()
            .nombre(UPDATED_NOMBRE);
        return estadoServicio;
    }

    @BeforeEach
    public void initTest() {
        estadoServicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoServicio() throws Exception {
        int databaseSizeBeforeCreate = estadoServicioRepository.findAll().size();

        // Create the EstadoServicio
        restEstadoServicioMockMvc.perform(post("/api/estado-servicios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoServicio)))
            .andExpect(status().isCreated());

        // Validate the EstadoServicio in the database
        List<EstadoServicio> estadoServicioList = estadoServicioRepository.findAll();
        assertThat(estadoServicioList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoServicio testEstadoServicio = estadoServicioList.get(estadoServicioList.size() - 1);
        assertThat(testEstadoServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createEstadoServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoServicioRepository.findAll().size();

        // Create the EstadoServicio with an existing ID
        estadoServicio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoServicioMockMvc.perform(post("/api/estado-servicios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoServicio)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoServicio in the database
        List<EstadoServicio> estadoServicioList = estadoServicioRepository.findAll();
        assertThat(estadoServicioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstadoServicios() throws Exception {
        // Initialize the database
        estadoServicioRepository.saveAndFlush(estadoServicio);

        // Get all the estadoServicioList
        restEstadoServicioMockMvc.perform(get("/api/estado-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getEstadoServicio() throws Exception {
        // Initialize the database
        estadoServicioRepository.saveAndFlush(estadoServicio);

        // Get the estadoServicio
        restEstadoServicioMockMvc.perform(get("/api/estado-servicios/{id}", estadoServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoServicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoServicio() throws Exception {
        // Get the estadoServicio
        restEstadoServicioMockMvc.perform(get("/api/estado-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoServicio() throws Exception {
        // Initialize the database
        estadoServicioRepository.saveAndFlush(estadoServicio);

        int databaseSizeBeforeUpdate = estadoServicioRepository.findAll().size();

        // Update the estadoServicio
        EstadoServicio updatedEstadoServicio = estadoServicioRepository.findById(estadoServicio.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoServicio are not directly saved in db
        em.detach(updatedEstadoServicio);
        updatedEstadoServicio
            .nombre(UPDATED_NOMBRE);

        restEstadoServicioMockMvc.perform(put("/api/estado-servicios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstadoServicio)))
            .andExpect(status().isOk());

        // Validate the EstadoServicio in the database
        List<EstadoServicio> estadoServicioList = estadoServicioRepository.findAll();
        assertThat(estadoServicioList).hasSize(databaseSizeBeforeUpdate);
        EstadoServicio testEstadoServicio = estadoServicioList.get(estadoServicioList.size() - 1);
        assertThat(testEstadoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoServicio() throws Exception {
        int databaseSizeBeforeUpdate = estadoServicioRepository.findAll().size();

        // Create the EstadoServicio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoServicioMockMvc.perform(put("/api/estado-servicios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoServicio)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoServicio in the database
        List<EstadoServicio> estadoServicioList = estadoServicioRepository.findAll();
        assertThat(estadoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoServicio() throws Exception {
        // Initialize the database
        estadoServicioRepository.saveAndFlush(estadoServicio);

        int databaseSizeBeforeDelete = estadoServicioRepository.findAll().size();

        // Delete the estadoServicio
        restEstadoServicioMockMvc.perform(delete("/api/estado-servicios/{id}", estadoServicio.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoServicio> estadoServicioList = estadoServicioRepository.findAll();
        assertThat(estadoServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
