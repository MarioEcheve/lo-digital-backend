package backend.web.rest;

import backend.BackendApp;
import backend.domain.Archivo;
import backend.repository.ArchivoRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArchivoResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class ArchivoResourceIT {

    private static final byte[] DEFAULT_ARCHIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    @Autowired
    private ArchivoRepository archivoRepository;

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

    private MockMvc restArchivoMockMvc;

    private Archivo archivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArchivoResource archivoResource = new ArchivoResource(archivoRepository);
        this.restArchivoMockMvc = MockMvcBuilders.standaloneSetup(archivoResource)
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
    public static Archivo createEntity(EntityManager em) {
        Archivo archivo = new Archivo()
            .archivo(DEFAULT_ARCHIVO)
            .archivoContentType(DEFAULT_ARCHIVO_CONTENT_TYPE)
            .descripcion(DEFAULT_DESCRIPCION)
            .size(DEFAULT_SIZE);
        return archivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archivo createUpdatedEntity(EntityManager em) {
        Archivo archivo = new Archivo()
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .size(UPDATED_SIZE);
        return archivo;
    }

    @BeforeEach
    public void initTest() {
        archivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivo() throws Exception {
        int databaseSizeBeforeCreate = archivoRepository.findAll().size();

        // Create the Archivo
        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isCreated());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeCreate + 1);
        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
        assertThat(testArchivo.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testArchivo.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
        assertThat(testArchivo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testArchivo.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createArchivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivoRepository.findAll().size();

        // Create the Archivo with an existing ID
        archivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isBadRequest());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = archivoRepository.findAll().size();
        // set the field null
        archivo.setSize(null);

        // Create the Archivo, which fails.

        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isBadRequest());

        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArchivos() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get all the archivoList
        restArchivoMockMvc.perform(get("/api/archivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }
    
    @Test
    @Transactional
    public void getArchivo() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", archivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archivo.getId().intValue()))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivo").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingArchivo() throws Exception {
        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivo() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();

        // Update the archivo
        Archivo updatedArchivo = archivoRepository.findById(archivo.getId()).get();
        // Disconnect from session so that the updates on updatedArchivo are not directly saved in db
        em.detach(updatedArchivo);
        updatedArchivo
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .size(UPDATED_SIZE);

        restArchivoMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArchivo)))
            .andExpect(status().isOk());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate);
        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
        assertThat(testArchivo.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testArchivo.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
        assertThat(testArchivo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testArchivo.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivo() throws Exception {
        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();

        // Create the Archivo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchivoMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isBadRequest());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArchivo() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        int databaseSizeBeforeDelete = archivoRepository.findAll().size();

        // Delete the archivo
        restArchivoMockMvc.perform(delete("/api/archivos/{id}", archivo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
