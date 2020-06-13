package backend.web.rest;

import backend.BackendApp;
import backend.domain.Entidad;
import backend.repository.EntidadRepository;
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
 * Integration tests for the {@link EntidadResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class EntidadResourceIT {

    private static final String DEFAULT_RUT = "AAAAAAAAAA";
    private static final String UPDATED_RUT = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EntidadRepository entidadRepository;

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

    private MockMvc restEntidadMockMvc;

    private Entidad entidad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntidadResource entidadResource = new EntidadResource(entidadRepository);
        this.restEntidadMockMvc = MockMvcBuilders.standaloneSetup(entidadResource)
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
    public static Entidad createEntity(EntityManager em) {
        Entidad entidad = new Entidad()
            .rut(DEFAULT_RUT)
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return entidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidad createUpdatedEntity(EntityManager em) {
        Entidad entidad = new Entidad()
            .rut(UPDATED_RUT)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        return entidad;
    }

    @BeforeEach
    public void initTest() {
        entidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidad() throws Exception {
        int databaseSizeBeforeCreate = entidadRepository.findAll().size();

        // Create the Entidad
        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isCreated());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate + 1);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getRut()).isEqualTo(DEFAULT_RUT);
        assertThat(testEntidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEntidad.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEntidad.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testEntidad.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void createEntidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadRepository.findAll().size();

        // Create the Entidad with an existing ID
        entidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRutIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadRepository.findAll().size();
        // set the field null
        entidad.setRut(null);

        // Create the Entidad, which fails.

        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadRepository.findAll().size();
        // set the field null
        entidad.setNombre(null);

        // Create the Entidad, which fails.

        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntidads() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get all the entidadList
        restEntidadMockMvc.perform(get("/api/entidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get the entidad
        restEntidadMockMvc.perform(get("/api/entidads/{id}", entidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entidad.getId().intValue()))
            .andExpect(jsonPath("$.rut").value(DEFAULT_RUT))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntidad() throws Exception {
        // Get the entidad
        restEntidadMockMvc.perform(get("/api/entidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Update the entidad
        Entidad updatedEntidad = entidadRepository.findById(entidad.getId()).get();
        // Disconnect from session so that the updates on updatedEntidad are not directly saved in db
        em.detach(updatedEntidad);
        updatedEntidad
            .rut(UPDATED_RUT)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restEntidadMockMvc.perform(put("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntidad)))
            .andExpect(status().isOk());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testEntidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEntidad.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEntidad.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testEntidad.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Create the Entidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadMockMvc.perform(put("/api/entidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidad)))
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeDelete = entidadRepository.findAll().size();

        // Delete the entidad
        restEntidadMockMvc.perform(delete("/api/entidads/{id}", entidad.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
