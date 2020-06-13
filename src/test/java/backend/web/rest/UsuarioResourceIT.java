package backend.web.rest;

import backend.BackendApp;
import backend.domain.Usuario;
import backend.repository.UsuarioRepository;
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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class UsuarioResourceIT {

    private static final String DEFAULT_RUT = "AAAAAAAAAA";
    private static final String UPDATED_RUT = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESION_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_PROFESION_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SECUNDARIO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SECUNDARIO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_SECUNDARIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_SECUNDARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioResource usuarioResource = new UsuarioResource(usuarioRepository);
        this.restUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usuarioResource)
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
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .rut(DEFAULT_RUT)
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .profesionOficio(DEFAULT_PROFESION_OFICIO)
            .emailPrincipal(DEFAULT_EMAIL_PRINCIPAL)
            .emailSecundario(DEFAULT_EMAIL_SECUNDARIO)
            .telefonoPrincipal(DEFAULT_TELEFONO_PRINCIPAL)
            .telefonoSecundario(DEFAULT_TELEFONO_SECUNDARIO)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION);
        return usuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .rut(UPDATED_RUT)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .profesionOficio(UPDATED_PROFESION_OFICIO)
            .emailPrincipal(UPDATED_EMAIL_PRINCIPAL)
            .emailSecundario(UPDATED_EMAIL_SECUNDARIO)
            .telefonoPrincipal(UPDATED_TELEFONO_PRINCIPAL)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getRut()).isEqualTo(DEFAULT_RUT);
        assertThat(testUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUsuario.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testUsuario.getApellidoMaterno()).isEqualTo(DEFAULT_APELLIDO_MATERNO);
        assertThat(testUsuario.getProfesionOficio()).isEqualTo(DEFAULT_PROFESION_OFICIO);
        assertThat(testUsuario.getEmailPrincipal()).isEqualTo(DEFAULT_EMAIL_PRINCIPAL);
        assertThat(testUsuario.getEmailSecundario()).isEqualTo(DEFAULT_EMAIL_SECUNDARIO);
        assertThat(testUsuario.getTelefonoPrincipal()).isEqualTo(DEFAULT_TELEFONO_PRINCIPAL);
        assertThat(testUsuario.getTelefonoSecundario()).isEqualTo(DEFAULT_TELEFONO_SECUNDARIO);
        assertThat(testUsuario.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testUsuario.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRutIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setRut(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNombre(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].profesionOficio").value(hasItem(DEFAULT_PROFESION_OFICIO)))
            .andExpect(jsonPath("$.[*].emailPrincipal").value(hasItem(DEFAULT_EMAIL_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].emailSecundario").value(hasItem(DEFAULT_EMAIL_SECUNDARIO)))
            .andExpect(jsonPath("$.[*].telefonoPrincipal").value(hasItem(DEFAULT_TELEFONO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefonoSecundario").value(hasItem(DEFAULT_TELEFONO_SECUNDARIO)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.rut").value(DEFAULT_RUT))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.profesionOficio").value(DEFAULT_PROFESION_OFICIO))
            .andExpect(jsonPath("$.emailPrincipal").value(DEFAULT_EMAIL_PRINCIPAL))
            .andExpect(jsonPath("$.emailSecundario").value(DEFAULT_EMAIL_SECUNDARIO))
            .andExpect(jsonPath("$.telefonoPrincipal").value(DEFAULT_TELEFONO_PRINCIPAL))
            .andExpect(jsonPath("$.telefonoSecundario").value(DEFAULT_TELEFONO_SECUNDARIO))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .rut(UPDATED_RUT)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .profesionOficio(UPDATED_PROFESION_OFICIO)
            .emailPrincipal(UPDATED_EMAIL_PRINCIPAL)
            .emailSecundario(UPDATED_EMAIL_SECUNDARIO)
            .telefonoPrincipal(UPDATED_TELEFONO_PRINCIPAL)
            .telefonoSecundario(UPDATED_TELEFONO_SECUNDARIO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuario)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testUsuario.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testUsuario.getProfesionOficio()).isEqualTo(UPDATED_PROFESION_OFICIO);
        assertThat(testUsuario.getEmailPrincipal()).isEqualTo(UPDATED_EMAIL_PRINCIPAL);
        assertThat(testUsuario.getEmailSecundario()).isEqualTo(UPDATED_EMAIL_SECUNDARIO);
        assertThat(testUsuario.getTelefonoPrincipal()).isEqualTo(UPDATED_TELEFONO_PRINCIPAL);
        assertThat(testUsuario.getTelefonoSecundario()).isEqualTo(UPDATED_TELEFONO_SECUNDARIO);
        assertThat(testUsuario.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testUsuario.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
