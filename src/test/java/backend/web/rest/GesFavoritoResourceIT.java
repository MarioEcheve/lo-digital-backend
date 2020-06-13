package backend.web.rest;

import backend.BackendApp;
import backend.domain.GesFavorito;
import backend.repository.GesFavoritoRepository;
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
 * Integration tests for the {@link GesFavoritoResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class GesFavoritoResourceIT {

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FAVORITO = false;
    private static final Boolean UPDATED_FAVORITO = true;

    @Autowired
    private GesFavoritoRepository gesFavoritoRepository;

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

    private MockMvc restGesFavoritoMockMvc;

    private GesFavorito gesFavorito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GesFavoritoResource gesFavoritoResource = new GesFavoritoResource(gesFavoritoRepository);
        this.restGesFavoritoMockMvc = MockMvcBuilders.standaloneSetup(gesFavoritoResource)
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
    public static GesFavorito createEntity(EntityManager em) {
        GesFavorito gesFavorito = new GesFavorito()
            .nota(DEFAULT_NOTA)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .favorito(DEFAULT_FAVORITO);
        return gesFavorito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GesFavorito createUpdatedEntity(EntityManager em) {
        GesFavorito gesFavorito = new GesFavorito()
            .nota(UPDATED_NOTA)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .favorito(UPDATED_FAVORITO);
        return gesFavorito;
    }

    @BeforeEach
    public void initTest() {
        gesFavorito = createEntity(em);
    }

    @Test
    @Transactional
    public void createGesFavorito() throws Exception {
        int databaseSizeBeforeCreate = gesFavoritoRepository.findAll().size();

        // Create the GesFavorito
        restGesFavoritoMockMvc.perform(post("/api/ges-favoritos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesFavorito)))
            .andExpect(status().isCreated());

        // Validate the GesFavorito in the database
        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeCreate + 1);
        GesFavorito testGesFavorito = gesFavoritoList.get(gesFavoritoList.size() - 1);
        assertThat(testGesFavorito.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testGesFavorito.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testGesFavorito.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testGesFavorito.isFavorito()).isEqualTo(DEFAULT_FAVORITO);
    }

    @Test
    @Transactional
    public void createGesFavoritoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gesFavoritoRepository.findAll().size();

        // Create the GesFavorito with an existing ID
        gesFavorito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGesFavoritoMockMvc.perform(post("/api/ges-favoritos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesFavorito)))
            .andExpect(status().isBadRequest());

        // Validate the GesFavorito in the database
        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gesFavoritoRepository.findAll().size();
        // set the field null
        gesFavorito.setNota(null);

        // Create the GesFavorito, which fails.

        restGesFavoritoMockMvc.perform(post("/api/ges-favoritos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesFavorito)))
            .andExpect(status().isBadRequest());

        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGesFavoritos() throws Exception {
        // Initialize the database
        gesFavoritoRepository.saveAndFlush(gesFavorito);

        // Get all the gesFavoritoList
        restGesFavoritoMockMvc.perform(get("/api/ges-favoritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gesFavorito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].favorito").value(hasItem(DEFAULT_FAVORITO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getGesFavorito() throws Exception {
        // Initialize the database
        gesFavoritoRepository.saveAndFlush(gesFavorito);

        // Get the gesFavorito
        restGesFavoritoMockMvc.perform(get("/api/ges-favoritos/{id}", gesFavorito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gesFavorito.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.favorito").value(DEFAULT_FAVORITO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGesFavorito() throws Exception {
        // Get the gesFavorito
        restGesFavoritoMockMvc.perform(get("/api/ges-favoritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGesFavorito() throws Exception {
        // Initialize the database
        gesFavoritoRepository.saveAndFlush(gesFavorito);

        int databaseSizeBeforeUpdate = gesFavoritoRepository.findAll().size();

        // Update the gesFavorito
        GesFavorito updatedGesFavorito = gesFavoritoRepository.findById(gesFavorito.getId()).get();
        // Disconnect from session so that the updates on updatedGesFavorito are not directly saved in db
        em.detach(updatedGesFavorito);
        updatedGesFavorito
            .nota(UPDATED_NOTA)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .favorito(UPDATED_FAVORITO);

        restGesFavoritoMockMvc.perform(put("/api/ges-favoritos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGesFavorito)))
            .andExpect(status().isOk());

        // Validate the GesFavorito in the database
        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeUpdate);
        GesFavorito testGesFavorito = gesFavoritoList.get(gesFavoritoList.size() - 1);
        assertThat(testGesFavorito.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testGesFavorito.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testGesFavorito.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testGesFavorito.isFavorito()).isEqualTo(UPDATED_FAVORITO);
    }

    @Test
    @Transactional
    public void updateNonExistingGesFavorito() throws Exception {
        int databaseSizeBeforeUpdate = gesFavoritoRepository.findAll().size();

        // Create the GesFavorito

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGesFavoritoMockMvc.perform(put("/api/ges-favoritos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gesFavorito)))
            .andExpect(status().isBadRequest());

        // Validate the GesFavorito in the database
        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGesFavorito() throws Exception {
        // Initialize the database
        gesFavoritoRepository.saveAndFlush(gesFavorito);

        int databaseSizeBeforeDelete = gesFavoritoRepository.findAll().size();

        // Delete the gesFavorito
        restGesFavoritoMockMvc.perform(delete("/api/ges-favoritos/{id}", gesFavorito.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GesFavorito> gesFavoritoList = gesFavoritoRepository.findAll();
        assertThat(gesFavoritoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
