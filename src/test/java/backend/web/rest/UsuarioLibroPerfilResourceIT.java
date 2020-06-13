package backend.web.rest;

import backend.BackendApp;
import backend.domain.UsuarioLibroPerfil;
import backend.repository.UsuarioLibroPerfilRepository;
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
 * Integration tests for the {@link UsuarioLibroPerfilResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class UsuarioLibroPerfilResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private UsuarioLibroPerfilRepository usuarioLibroPerfilRepository;

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

    private MockMvc restUsuarioLibroPerfilMockMvc;

    private UsuarioLibroPerfil usuarioLibroPerfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioLibroPerfilResource usuarioLibroPerfilResource = new UsuarioLibroPerfilResource(usuarioLibroPerfilRepository);
        this.restUsuarioLibroPerfilMockMvc = MockMvcBuilders.standaloneSetup(usuarioLibroPerfilResource)
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
    public static UsuarioLibroPerfil createEntity(EntityManager em) {
        UsuarioLibroPerfil usuarioLibroPerfil = new UsuarioLibroPerfil()
            .nombre(DEFAULT_NOMBRE);
        return usuarioLibroPerfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioLibroPerfil createUpdatedEntity(EntityManager em) {
        UsuarioLibroPerfil usuarioLibroPerfil = new UsuarioLibroPerfil()
            .nombre(UPDATED_NOMBRE);
        return usuarioLibroPerfil;
    }

    @BeforeEach
    public void initTest() {
        usuarioLibroPerfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioLibroPerfil() throws Exception {
        int databaseSizeBeforeCreate = usuarioLibroPerfilRepository.findAll().size();

        // Create the UsuarioLibroPerfil
        restUsuarioLibroPerfilMockMvc.perform(post("/api/usuario-libro-perfils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibroPerfil)))
            .andExpect(status().isCreated());

        // Validate the UsuarioLibroPerfil in the database
        List<UsuarioLibroPerfil> usuarioLibroPerfilList = usuarioLibroPerfilRepository.findAll();
        assertThat(usuarioLibroPerfilList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioLibroPerfil testUsuarioLibroPerfil = usuarioLibroPerfilList.get(usuarioLibroPerfilList.size() - 1);
        assertThat(testUsuarioLibroPerfil.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createUsuarioLibroPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioLibroPerfilRepository.findAll().size();

        // Create the UsuarioLibroPerfil with an existing ID
        usuarioLibroPerfil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioLibroPerfilMockMvc.perform(post("/api/usuario-libro-perfils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibroPerfil)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioLibroPerfil in the database
        List<UsuarioLibroPerfil> usuarioLibroPerfilList = usuarioLibroPerfilRepository.findAll();
        assertThat(usuarioLibroPerfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsuarioLibroPerfils() throws Exception {
        // Initialize the database
        usuarioLibroPerfilRepository.saveAndFlush(usuarioLibroPerfil);

        // Get all the usuarioLibroPerfilList
        restUsuarioLibroPerfilMockMvc.perform(get("/api/usuario-libro-perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioLibroPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getUsuarioLibroPerfil() throws Exception {
        // Initialize the database
        usuarioLibroPerfilRepository.saveAndFlush(usuarioLibroPerfil);

        // Get the usuarioLibroPerfil
        restUsuarioLibroPerfilMockMvc.perform(get("/api/usuario-libro-perfils/{id}", usuarioLibroPerfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioLibroPerfil.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarioLibroPerfil() throws Exception {
        // Get the usuarioLibroPerfil
        restUsuarioLibroPerfilMockMvc.perform(get("/api/usuario-libro-perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioLibroPerfil() throws Exception {
        // Initialize the database
        usuarioLibroPerfilRepository.saveAndFlush(usuarioLibroPerfil);

        int databaseSizeBeforeUpdate = usuarioLibroPerfilRepository.findAll().size();

        // Update the usuarioLibroPerfil
        UsuarioLibroPerfil updatedUsuarioLibroPerfil = usuarioLibroPerfilRepository.findById(usuarioLibroPerfil.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioLibroPerfil are not directly saved in db
        em.detach(updatedUsuarioLibroPerfil);
        updatedUsuarioLibroPerfil
            .nombre(UPDATED_NOMBRE);

        restUsuarioLibroPerfilMockMvc.perform(put("/api/usuario-libro-perfils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuarioLibroPerfil)))
            .andExpect(status().isOk());

        // Validate the UsuarioLibroPerfil in the database
        List<UsuarioLibroPerfil> usuarioLibroPerfilList = usuarioLibroPerfilRepository.findAll();
        assertThat(usuarioLibroPerfilList).hasSize(databaseSizeBeforeUpdate);
        UsuarioLibroPerfil testUsuarioLibroPerfil = usuarioLibroPerfilList.get(usuarioLibroPerfilList.size() - 1);
        assertThat(testUsuarioLibroPerfil.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioLibroPerfil() throws Exception {
        int databaseSizeBeforeUpdate = usuarioLibroPerfilRepository.findAll().size();

        // Create the UsuarioLibroPerfil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioLibroPerfilMockMvc.perform(put("/api/usuario-libro-perfils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioLibroPerfil)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioLibroPerfil in the database
        List<UsuarioLibroPerfil> usuarioLibroPerfilList = usuarioLibroPerfilRepository.findAll();
        assertThat(usuarioLibroPerfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarioLibroPerfil() throws Exception {
        // Initialize the database
        usuarioLibroPerfilRepository.saveAndFlush(usuarioLibroPerfil);

        int databaseSizeBeforeDelete = usuarioLibroPerfilRepository.findAll().size();

        // Delete the usuarioLibroPerfil
        restUsuarioLibroPerfilMockMvc.perform(delete("/api/usuario-libro-perfils/{id}", usuarioLibroPerfil.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioLibroPerfil> usuarioLibroPerfilList = usuarioLibroPerfilRepository.findAll();
        assertThat(usuarioLibroPerfilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
