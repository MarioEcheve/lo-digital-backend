package backend.web.rest;

import backend.BackendApp;
import backend.domain.UsuarioDependencia;
import backend.repository.UsuarioDependenciaRepository;
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
 * Integration tests for the {@link UsuarioDependenciaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class UsuarioDependenciaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_RUT = "AAAAAAAAAA";
    private static final String UPDATED_RUT = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private UsuarioDependenciaRepository usuarioDependenciaRepository;

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

    private MockMvc restUsuarioDependenciaMockMvc;

    private UsuarioDependencia usuarioDependencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioDependenciaResource usuarioDependenciaResource = new UsuarioDependenciaResource(usuarioDependenciaRepository);
        this.restUsuarioDependenciaMockMvc = MockMvcBuilders.standaloneSetup(usuarioDependenciaResource)
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
    public static UsuarioDependencia createEntity(EntityManager em) {
        UsuarioDependencia usuarioDependencia = new UsuarioDependencia()
            .nombre(DEFAULT_NOMBRE)
            .rut(DEFAULT_RUT)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .estado(DEFAULT_ESTADO);
        return usuarioDependencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioDependencia createUpdatedEntity(EntityManager em) {
        UsuarioDependencia usuarioDependencia = new UsuarioDependencia()
            .nombre(UPDATED_NOMBRE)
            .rut(UPDATED_RUT)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .estado(UPDATED_ESTADO);
        return usuarioDependencia;
    }

    @BeforeEach
    public void initTest() {
        usuarioDependencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioDependencia() throws Exception {
        int databaseSizeBeforeCreate = usuarioDependenciaRepository.findAll().size();

        // Create the UsuarioDependencia
        restUsuarioDependenciaMockMvc.perform(post("/api/usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDependencia)))
            .andExpect(status().isCreated());

        // Validate the UsuarioDependencia in the database
        List<UsuarioDependencia> usuarioDependenciaList = usuarioDependenciaRepository.findAll();
        assertThat(usuarioDependenciaList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioDependencia testUsuarioDependencia = usuarioDependenciaList.get(usuarioDependenciaList.size() - 1);
        assertThat(testUsuarioDependencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUsuarioDependencia.getRut()).isEqualTo(DEFAULT_RUT);
        assertThat(testUsuarioDependencia.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testUsuarioDependencia.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testUsuarioDependencia.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createUsuarioDependenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioDependenciaRepository.findAll().size();

        // Create the UsuarioDependencia with an existing ID
        usuarioDependencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioDependenciaMockMvc.perform(post("/api/usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDependencia)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioDependencia in the database
        List<UsuarioDependencia> usuarioDependenciaList = usuarioDependenciaRepository.findAll();
        assertThat(usuarioDependenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsuarioDependencias() throws Exception {
        // Initialize the database
        usuarioDependenciaRepository.saveAndFlush(usuarioDependencia);

        // Get all the usuarioDependenciaList
        restUsuarioDependenciaMockMvc.perform(get("/api/usuario-dependencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioDependencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUsuarioDependencia() throws Exception {
        // Initialize the database
        usuarioDependenciaRepository.saveAndFlush(usuarioDependencia);

        // Get the usuarioDependencia
        restUsuarioDependenciaMockMvc.perform(get("/api/usuario-dependencias/{id}", usuarioDependencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioDependencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.rut").value(DEFAULT_RUT))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarioDependencia() throws Exception {
        // Get the usuarioDependencia
        restUsuarioDependenciaMockMvc.perform(get("/api/usuario-dependencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioDependencia() throws Exception {
        // Initialize the database
        usuarioDependenciaRepository.saveAndFlush(usuarioDependencia);

        int databaseSizeBeforeUpdate = usuarioDependenciaRepository.findAll().size();

        // Update the usuarioDependencia
        UsuarioDependencia updatedUsuarioDependencia = usuarioDependenciaRepository.findById(usuarioDependencia.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioDependencia are not directly saved in db
        em.detach(updatedUsuarioDependencia);
        updatedUsuarioDependencia
            .nombre(UPDATED_NOMBRE)
            .rut(UPDATED_RUT)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .estado(UPDATED_ESTADO);

        restUsuarioDependenciaMockMvc.perform(put("/api/usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuarioDependencia)))
            .andExpect(status().isOk());

        // Validate the UsuarioDependencia in the database
        List<UsuarioDependencia> usuarioDependenciaList = usuarioDependenciaRepository.findAll();
        assertThat(usuarioDependenciaList).hasSize(databaseSizeBeforeUpdate);
        UsuarioDependencia testUsuarioDependencia = usuarioDependenciaList.get(usuarioDependenciaList.size() - 1);
        assertThat(testUsuarioDependencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuarioDependencia.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testUsuarioDependencia.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testUsuarioDependencia.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testUsuarioDependencia.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioDependencia() throws Exception {
        int databaseSizeBeforeUpdate = usuarioDependenciaRepository.findAll().size();

        // Create the UsuarioDependencia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioDependenciaMockMvc.perform(put("/api/usuario-dependencias")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDependencia)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioDependencia in the database
        List<UsuarioDependencia> usuarioDependenciaList = usuarioDependenciaRepository.findAll();
        assertThat(usuarioDependenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarioDependencia() throws Exception {
        // Initialize the database
        usuarioDependenciaRepository.saveAndFlush(usuarioDependencia);

        int databaseSizeBeforeDelete = usuarioDependenciaRepository.findAll().size();

        // Delete the usuarioDependencia
        restUsuarioDependenciaMockMvc.perform(delete("/api/usuario-dependencias/{id}", usuarioDependencia.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioDependencia> usuarioDependenciaList = usuarioDependenciaRepository.findAll();
        assertThat(usuarioDependenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
