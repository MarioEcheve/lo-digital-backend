package backend.web.rest;

import backend.BackendApp;
import backend.domain.Libro;
import backend.repository.LibroRepository;
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
 * Integration tests for the {@link LibroResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class LibroResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_APERTURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_APERTURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_CIERRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CIERRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_APERTURA_MANDANTE = false;
    private static final Boolean UPDATED_APERTURA_MANDANTE = true;

    private static final Boolean DEFAULT_APERTURA_CONTRATISTA = false;
    private static final Boolean UPDATED_APERTURA_CONTRATISTA = true;

    private static final Boolean DEFAULT_ESCRITURA_MANDANTE = false;
    private static final Boolean UPDATED_ESCRITURA_MANDANTE = true;

    private static final Boolean DEFAULT_ESCRITURA_CONTRATISTA = false;
    private static final Boolean UPDATED_ESCRITURA_CONTRATISTA = true;

    private static final Boolean DEFAULT_CIERRE_MANDANTE = false;
    private static final Boolean UPDATED_CIERRE_MANDANTE = true;

    private static final Boolean DEFAULT_CIERRE_CONTRATISTA = false;
    private static final Boolean UPDATED_CIERRE_CONTRATISTA = true;

    private static final Boolean DEFAULT_LECTURA_MANDANTE = false;
    private static final Boolean UPDATED_LECTURA_MANDANTE = true;

    private static final Boolean DEFAULT_LECTURA_CONTRATISTA = false;
    private static final Boolean UPDATED_LECTURA_CONTRATISTA = true;

    @Autowired
    private LibroRepository libroRepository;

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

    private MockMvc restLibroMockMvc;

    private Libro libro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibroResource libroResource = new LibroResource(libroRepository);
        this.restLibroMockMvc = MockMvcBuilders.standaloneSetup(libroResource)
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
    public static Libro createEntity(EntityManager em) {
        Libro libro = new Libro()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaApertura(DEFAULT_FECHA_APERTURA)
            .fechaCierre(DEFAULT_FECHA_CIERRE)
            .aperturaMandante(DEFAULT_APERTURA_MANDANTE)
            .aperturaContratista(DEFAULT_APERTURA_CONTRATISTA)
            .escrituraMandante(DEFAULT_ESCRITURA_MANDANTE)
            .escrituraContratista(DEFAULT_ESCRITURA_CONTRATISTA)
            .cierreMandante(DEFAULT_CIERRE_MANDANTE)
            .cierreContratista(DEFAULT_CIERRE_CONTRATISTA)
            .lecturaMandante(DEFAULT_LECTURA_MANDANTE)
            .lecturaContratista(DEFAULT_LECTURA_CONTRATISTA);
        return libro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Libro createUpdatedEntity(EntityManager em) {
        Libro libro = new Libro()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaApertura(UPDATED_FECHA_APERTURA)
            .fechaCierre(UPDATED_FECHA_CIERRE)
            .aperturaMandante(UPDATED_APERTURA_MANDANTE)
            .aperturaContratista(UPDATED_APERTURA_CONTRATISTA)
            .escrituraMandante(UPDATED_ESCRITURA_MANDANTE)
            .escrituraContratista(UPDATED_ESCRITURA_CONTRATISTA)
            .cierreMandante(UPDATED_CIERRE_MANDANTE)
            .cierreContratista(UPDATED_CIERRE_CONTRATISTA)
            .lecturaMandante(UPDATED_LECTURA_MANDANTE)
            .lecturaContratista(UPDATED_LECTURA_CONTRATISTA);
        return libro;
    }

    @BeforeEach
    public void initTest() {
        libro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibro() throws Exception {
        int databaseSizeBeforeCreate = libroRepository.findAll().size();

        // Create the Libro
        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isCreated());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeCreate + 1);
        Libro testLibro = libroList.get(libroList.size() - 1);
        assertThat(testLibro.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLibro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLibro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testLibro.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testLibro.getFechaApertura()).isEqualTo(DEFAULT_FECHA_APERTURA);
        assertThat(testLibro.getFechaCierre()).isEqualTo(DEFAULT_FECHA_CIERRE);
        assertThat(testLibro.isAperturaMandante()).isEqualTo(DEFAULT_APERTURA_MANDANTE);
        assertThat(testLibro.isAperturaContratista()).isEqualTo(DEFAULT_APERTURA_CONTRATISTA);
        assertThat(testLibro.isEscrituraMandante()).isEqualTo(DEFAULT_ESCRITURA_MANDANTE);
        assertThat(testLibro.isEscrituraContratista()).isEqualTo(DEFAULT_ESCRITURA_CONTRATISTA);
        assertThat(testLibro.isCierreMandante()).isEqualTo(DEFAULT_CIERRE_MANDANTE);
        assertThat(testLibro.isCierreContratista()).isEqualTo(DEFAULT_CIERRE_CONTRATISTA);
        assertThat(testLibro.isLecturaMandante()).isEqualTo(DEFAULT_LECTURA_MANDANTE);
        assertThat(testLibro.isLecturaContratista()).isEqualTo(DEFAULT_LECTURA_CONTRATISTA);
    }

    @Test
    @Transactional
    public void createLibroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libroRepository.findAll().size();

        // Create the Libro with an existing ID
        libro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLibros() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        // Get all the libroList
        restLibroMockMvc.perform(get("/api/libros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libro.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaApertura").value(hasItem(DEFAULT_FECHA_APERTURA.toString())))
            .andExpect(jsonPath("$.[*].fechaCierre").value(hasItem(DEFAULT_FECHA_CIERRE.toString())))
            .andExpect(jsonPath("$.[*].aperturaMandante").value(hasItem(DEFAULT_APERTURA_MANDANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].aperturaContratista").value(hasItem(DEFAULT_APERTURA_CONTRATISTA.booleanValue())))
            .andExpect(jsonPath("$.[*].escrituraMandante").value(hasItem(DEFAULT_ESCRITURA_MANDANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].escrituraContratista").value(hasItem(DEFAULT_ESCRITURA_CONTRATISTA.booleanValue())))
            .andExpect(jsonPath("$.[*].cierreMandante").value(hasItem(DEFAULT_CIERRE_MANDANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].cierreContratista").value(hasItem(DEFAULT_CIERRE_CONTRATISTA.booleanValue())))
            .andExpect(jsonPath("$.[*].lecturaMandante").value(hasItem(DEFAULT_LECTURA_MANDANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].lecturaContratista").value(hasItem(DEFAULT_LECTURA_CONTRATISTA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLibro() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        // Get the libro
        restLibroMockMvc.perform(get("/api/libros/{id}", libro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(libro.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaApertura").value(DEFAULT_FECHA_APERTURA.toString()))
            .andExpect(jsonPath("$.fechaCierre").value(DEFAULT_FECHA_CIERRE.toString()))
            .andExpect(jsonPath("$.aperturaMandante").value(DEFAULT_APERTURA_MANDANTE.booleanValue()))
            .andExpect(jsonPath("$.aperturaContratista").value(DEFAULT_APERTURA_CONTRATISTA.booleanValue()))
            .andExpect(jsonPath("$.escrituraMandante").value(DEFAULT_ESCRITURA_MANDANTE.booleanValue()))
            .andExpect(jsonPath("$.escrituraContratista").value(DEFAULT_ESCRITURA_CONTRATISTA.booleanValue()))
            .andExpect(jsonPath("$.cierreMandante").value(DEFAULT_CIERRE_MANDANTE.booleanValue()))
            .andExpect(jsonPath("$.cierreContratista").value(DEFAULT_CIERRE_CONTRATISTA.booleanValue()))
            .andExpect(jsonPath("$.lecturaMandante").value(DEFAULT_LECTURA_MANDANTE.booleanValue()))
            .andExpect(jsonPath("$.lecturaContratista").value(DEFAULT_LECTURA_CONTRATISTA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLibro() throws Exception {
        // Get the libro
        restLibroMockMvc.perform(get("/api/libros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibro() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        int databaseSizeBeforeUpdate = libroRepository.findAll().size();

        // Update the libro
        Libro updatedLibro = libroRepository.findById(libro.getId()).get();
        // Disconnect from session so that the updates on updatedLibro are not directly saved in db
        em.detach(updatedLibro);
        updatedLibro
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaApertura(UPDATED_FECHA_APERTURA)
            .fechaCierre(UPDATED_FECHA_CIERRE)
            .aperturaMandante(UPDATED_APERTURA_MANDANTE)
            .aperturaContratista(UPDATED_APERTURA_CONTRATISTA)
            .escrituraMandante(UPDATED_ESCRITURA_MANDANTE)
            .escrituraContratista(UPDATED_ESCRITURA_CONTRATISTA)
            .cierreMandante(UPDATED_CIERRE_MANDANTE)
            .cierreContratista(UPDATED_CIERRE_CONTRATISTA)
            .lecturaMandante(UPDATED_LECTURA_MANDANTE)
            .lecturaContratista(UPDATED_LECTURA_CONTRATISTA);

        restLibroMockMvc.perform(put("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibro)))
            .andExpect(status().isOk());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeUpdate);
        Libro testLibro = libroList.get(libroList.size() - 1);
        assertThat(testLibro.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLibro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLibro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testLibro.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testLibro.getFechaApertura()).isEqualTo(UPDATED_FECHA_APERTURA);
        assertThat(testLibro.getFechaCierre()).isEqualTo(UPDATED_FECHA_CIERRE);
        assertThat(testLibro.isAperturaMandante()).isEqualTo(UPDATED_APERTURA_MANDANTE);
        assertThat(testLibro.isAperturaContratista()).isEqualTo(UPDATED_APERTURA_CONTRATISTA);
        assertThat(testLibro.isEscrituraMandante()).isEqualTo(UPDATED_ESCRITURA_MANDANTE);
        assertThat(testLibro.isEscrituraContratista()).isEqualTo(UPDATED_ESCRITURA_CONTRATISTA);
        assertThat(testLibro.isCierreMandante()).isEqualTo(UPDATED_CIERRE_MANDANTE);
        assertThat(testLibro.isCierreContratista()).isEqualTo(UPDATED_CIERRE_CONTRATISTA);
        assertThat(testLibro.isLecturaMandante()).isEqualTo(UPDATED_LECTURA_MANDANTE);
        assertThat(testLibro.isLecturaContratista()).isEqualTo(UPDATED_LECTURA_CONTRATISTA);
    }

    @Test
    @Transactional
    public void updateNonExistingLibro() throws Exception {
        int databaseSizeBeforeUpdate = libroRepository.findAll().size();

        // Create the Libro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibroMockMvc.perform(put("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLibro() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        int databaseSizeBeforeDelete = libroRepository.findAll().size();

        // Delete the libro
        restLibroMockMvc.perform(delete("/api/libros/{id}", libro.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
