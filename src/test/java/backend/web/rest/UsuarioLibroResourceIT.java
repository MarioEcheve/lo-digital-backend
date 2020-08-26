package backend.web.rest;

import backend.BackendApp;
import backend.domain.UsuarioLibro;
import backend.repository.UsuarioLibroRepository;
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
 * Integration tests for the {@link UsuarioLibroResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class UsuarioLibroResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final String DEFAULT_CARGO_FUNCION = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_FUNCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ADMIN_ACTIVO = false;
    private static final Boolean UPDATED_ADMIN_ACTIVO = true;

    @Autowired
    private UsuarioLibroRepository usuarioLibroRepository;

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

    private MockMvc restUsuarioLibroMockMvc;

    private UsuarioLibro usuarioLibro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioLibroResource usuarioLibroResource = new UsuarioLibroResource(usuarioLibroRepository);
        this.restUsuarioLibroMockMvc = MockMvcBuilders.standaloneSetup(usuarioLibroResource)
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
    public static UsuarioLibro createEntity(EntityManager em) {
        UsuarioLibro usuarioLibro = new UsuarioLibro()
            .estado(DEFAULT_ESTADO)
            .cargoFuncion(DEFAULT_CARGO_FUNCION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .adminActivo(DEFAULT_ADMIN_ACTIVO);
        return usuarioLibro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioLibro createUpdatedEntity(EntityManager em) {
        UsuarioLibro usuarioLibro = new UsuarioLibro()
            .estado(UPDATED_ESTADO)
            .cargoFuncion(UPDATED_CARGO_FUNCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .adminActivo(UPDATED_ADMIN_ACTIVO);
        return usuarioLibro;
    }

    @BeforeEach
    public void initTest() {
        usuarioLibro = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioLibro() throws Exception {
        int databaseSizeBeforeCreate = usuarioLibroRepository.findAll().size();

        // Create the UsuarioLibro
        restUsuarioLibroMockMvc.perform(post("/api/usuario-libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibro)))
            .andExpect(status().isCreated());

        // Validate the UsuarioLibro in the database
        List<UsuarioLibro> usuarioLibroList = usuarioLibroRepository.findAll();
        assertThat(usuarioLibroList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioLibro testUsuarioLibro = usuarioLibroList.get(usuarioLibroList.size() - 1);
        assertThat(testUsuarioLibro.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuarioLibro.getCargoFuncion()).isEqualTo(DEFAULT_CARGO_FUNCION);
        assertThat(testUsuarioLibro.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testUsuarioLibro.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testUsuarioLibro.isAdminActivo()).isEqualTo(DEFAULT_ADMIN_ACTIVO);
    }

    @Test
    @Transactional
    public void createUsuarioLibroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioLibroRepository.findAll().size();

        // Create the UsuarioLibro with an existing ID
        usuarioLibro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioLibroMockMvc.perform(post("/api/usuario-libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibro)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioLibro in the database
        List<UsuarioLibro> usuarioLibroList = usuarioLibroRepository.findAll();
        assertThat(usuarioLibroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsuarioLibros() throws Exception {
        // Initialize the database
        usuarioLibroRepository.saveAndFlush(usuarioLibro);

        // Get all the usuarioLibroList
        restUsuarioLibroMockMvc.perform(get("/api/usuario-libros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioLibro.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].cargoFuncion").value(hasItem(DEFAULT_CARGO_FUNCION)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].adminActivo").value(hasItem(DEFAULT_ADMIN_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUsuarioLibro() throws Exception {
        // Initialize the database
        usuarioLibroRepository.saveAndFlush(usuarioLibro);

        // Get the usuarioLibro
        restUsuarioLibroMockMvc.perform(get("/api/usuario-libros/{id}", usuarioLibro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioLibro.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.cargoFuncion").value(DEFAULT_CARGO_FUNCION))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.adminActivo").value(DEFAULT_ADMIN_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarioLibro() throws Exception {
        // Get the usuarioLibro
        restUsuarioLibroMockMvc.perform(get("/api/usuario-libros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioLibro() throws Exception {
        // Initialize the database
        usuarioLibroRepository.saveAndFlush(usuarioLibro);

        int databaseSizeBeforeUpdate = usuarioLibroRepository.findAll().size();

        // Update the usuarioLibro
        UsuarioLibro updatedUsuarioLibro = usuarioLibroRepository.findById(usuarioLibro.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioLibro are not directly saved in db
        em.detach(updatedUsuarioLibro);
        updatedUsuarioLibro
            .estado(UPDATED_ESTADO)
            .cargoFuncion(UPDATED_CARGO_FUNCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .adminActivo(UPDATED_ADMIN_ACTIVO);

        restUsuarioLibroMockMvc.perform(put("/api/usuario-libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuarioLibro)))
            .andExpect(status().isOk());

        // Validate the UsuarioLibro in the database
        List<UsuarioLibro> usuarioLibroList = usuarioLibroRepository.findAll();
        assertThat(usuarioLibroList).hasSize(databaseSizeBeforeUpdate);
        UsuarioLibro testUsuarioLibro = usuarioLibroList.get(usuarioLibroList.size() - 1);
        assertThat(testUsuarioLibro.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuarioLibro.getCargoFuncion()).isEqualTo(UPDATED_CARGO_FUNCION);
        assertThat(testUsuarioLibro.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testUsuarioLibro.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testUsuarioLibro.isAdminActivo()).isEqualTo(UPDATED_ADMIN_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioLibro() throws Exception {
        int databaseSizeBeforeUpdate = usuarioLibroRepository.findAll().size();

        // Create the UsuarioLibro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioLibroMockMvc.perform(put("/api/usuario-libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibro)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioLibro in the database
        List<UsuarioLibro> usuarioLibroList = usuarioLibroRepository.findAll();
        assertThat(usuarioLibroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarioLibro() throws Exception {
        // Initialize the database
        usuarioLibroRepository.saveAndFlush(usuarioLibro);

        int databaseSizeBeforeDelete = usuarioLibroRepository.findAll().size();

        // Delete the usuarioLibro
        restUsuarioLibroMockMvc.perform(delete("/api/usuario-libros/{id}", usuarioLibro.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioLibro> usuarioLibroList = usuarioLibroRepository.findAll();
        assertThat(usuarioLibroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
