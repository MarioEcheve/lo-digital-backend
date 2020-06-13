package backend.web.rest;

import backend.BackendApp;
import backend.domain.TipoMonto;
import backend.repository.TipoMontoRepository;
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
 * Integration tests for the {@link TipoMontoResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class TipoMontoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoMontoRepository tipoMontoRepository;

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

    private MockMvc restTipoMontoMockMvc;

    private TipoMonto tipoMonto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoMontoResource tipoMontoResource = new TipoMontoResource(tipoMontoRepository);
        this.restTipoMontoMockMvc = MockMvcBuilders.standaloneSetup(tipoMontoResource)
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
    public static TipoMonto createEntity(EntityManager em) {
        TipoMonto tipoMonto = new TipoMonto()
            .nombre(DEFAULT_NOMBRE);
        return tipoMonto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMonto createUpdatedEntity(EntityManager em) {
        TipoMonto tipoMonto = new TipoMonto()
            .nombre(UPDATED_NOMBRE);
        return tipoMonto;
    }

    @BeforeEach
    public void initTest() {
        tipoMonto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoMonto() throws Exception {
        int databaseSizeBeforeCreate = tipoMontoRepository.findAll().size();

        // Create the TipoMonto
        restTipoMontoMockMvc.perform(post("/api/tipo-montos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMonto)))
            .andExpect(status().isCreated());

        // Validate the TipoMonto in the database
        List<TipoMonto> tipoMontoList = tipoMontoRepository.findAll();
        assertThat(tipoMontoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMonto testTipoMonto = tipoMontoList.get(tipoMontoList.size() - 1);
        assertThat(testTipoMonto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoMontoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoMontoRepository.findAll().size();

        // Create the TipoMonto with an existing ID
        tipoMonto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMontoMockMvc.perform(post("/api/tipo-montos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMonto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMonto in the database
        List<TipoMonto> tipoMontoList = tipoMontoRepository.findAll();
        assertThat(tipoMontoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoMontos() throws Exception {
        // Initialize the database
        tipoMontoRepository.saveAndFlush(tipoMonto);

        // Get all the tipoMontoList
        restTipoMontoMockMvc.perform(get("/api/tipo-montos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMonto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getTipoMonto() throws Exception {
        // Initialize the database
        tipoMontoRepository.saveAndFlush(tipoMonto);

        // Get the tipoMonto
        restTipoMontoMockMvc.perform(get("/api/tipo-montos/{id}", tipoMonto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMonto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingTipoMonto() throws Exception {
        // Get the tipoMonto
        restTipoMontoMockMvc.perform(get("/api/tipo-montos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoMonto() throws Exception {
        // Initialize the database
        tipoMontoRepository.saveAndFlush(tipoMonto);

        int databaseSizeBeforeUpdate = tipoMontoRepository.findAll().size();

        // Update the tipoMonto
        TipoMonto updatedTipoMonto = tipoMontoRepository.findById(tipoMonto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoMonto are not directly saved in db
        em.detach(updatedTipoMonto);
        updatedTipoMonto
            .nombre(UPDATED_NOMBRE);

        restTipoMontoMockMvc.perform(put("/api/tipo-montos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoMonto)))
            .andExpect(status().isOk());

        // Validate the TipoMonto in the database
        List<TipoMonto> tipoMontoList = tipoMontoRepository.findAll();
        assertThat(tipoMontoList).hasSize(databaseSizeBeforeUpdate);
        TipoMonto testTipoMonto = tipoMontoList.get(tipoMontoList.size() - 1);
        assertThat(testTipoMonto.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoMonto() throws Exception {
        int databaseSizeBeforeUpdate = tipoMontoRepository.findAll().size();

        // Create the TipoMonto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMontoMockMvc.perform(put("/api/tipo-montos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMonto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMonto in the database
        List<TipoMonto> tipoMontoList = tipoMontoRepository.findAll();
        assertThat(tipoMontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoMonto() throws Exception {
        // Initialize the database
        tipoMontoRepository.saveAndFlush(tipoMonto);

        int databaseSizeBeforeDelete = tipoMontoRepository.findAll().size();

        // Delete the tipoMonto
        restTipoMontoMockMvc.perform(delete("/api/tipo-montos/{id}", tipoMonto.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoMonto> tipoMontoList = tipoMontoRepository.findAll();
        assertThat(tipoMontoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
