package backend.web.rest;

import backend.BackendApp;
import backend.domain.TipoMoneda;
import backend.repository.TipoMonedaRepository;
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
 * Integration tests for the {@link TipoMonedaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class TipoMonedaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoMonedaRepository tipoMonedaRepository;

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

    private MockMvc restTipoMonedaMockMvc;

    private TipoMoneda tipoMoneda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoMonedaResource tipoMonedaResource = new TipoMonedaResource(tipoMonedaRepository);
        this.restTipoMonedaMockMvc = MockMvcBuilders.standaloneSetup(tipoMonedaResource)
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
    public static TipoMoneda createEntity(EntityManager em) {
        TipoMoneda tipoMoneda = new TipoMoneda()
            .nombre(DEFAULT_NOMBRE);
        return tipoMoneda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMoneda createUpdatedEntity(EntityManager em) {
        TipoMoneda tipoMoneda = new TipoMoneda()
            .nombre(UPDATED_NOMBRE);
        return tipoMoneda;
    }

    @BeforeEach
    public void initTest() {
        tipoMoneda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoMoneda() throws Exception {
        int databaseSizeBeforeCreate = tipoMonedaRepository.findAll().size();

        // Create the TipoMoneda
        restTipoMonedaMockMvc.perform(post("/api/tipo-monedas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMoneda)))
            .andExpect(status().isCreated());

        // Validate the TipoMoneda in the database
        List<TipoMoneda> tipoMonedaList = tipoMonedaRepository.findAll();
        assertThat(tipoMonedaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMoneda testTipoMoneda = tipoMonedaList.get(tipoMonedaList.size() - 1);
        assertThat(testTipoMoneda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoMonedaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoMonedaRepository.findAll().size();

        // Create the TipoMoneda with an existing ID
        tipoMoneda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMonedaMockMvc.perform(post("/api/tipo-monedas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMoneda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMoneda in the database
        List<TipoMoneda> tipoMonedaList = tipoMonedaRepository.findAll();
        assertThat(tipoMonedaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoMonedas() throws Exception {
        // Initialize the database
        tipoMonedaRepository.saveAndFlush(tipoMoneda);

        // Get all the tipoMonedaList
        restTipoMonedaMockMvc.perform(get("/api/tipo-monedas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMoneda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getTipoMoneda() throws Exception {
        // Initialize the database
        tipoMonedaRepository.saveAndFlush(tipoMoneda);

        // Get the tipoMoneda
        restTipoMonedaMockMvc.perform(get("/api/tipo-monedas/{id}", tipoMoneda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMoneda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingTipoMoneda() throws Exception {
        // Get the tipoMoneda
        restTipoMonedaMockMvc.perform(get("/api/tipo-monedas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoMoneda() throws Exception {
        // Initialize the database
        tipoMonedaRepository.saveAndFlush(tipoMoneda);

        int databaseSizeBeforeUpdate = tipoMonedaRepository.findAll().size();

        // Update the tipoMoneda
        TipoMoneda updatedTipoMoneda = tipoMonedaRepository.findById(tipoMoneda.getId()).get();
        // Disconnect from session so that the updates on updatedTipoMoneda are not directly saved in db
        em.detach(updatedTipoMoneda);
        updatedTipoMoneda
            .nombre(UPDATED_NOMBRE);

        restTipoMonedaMockMvc.perform(put("/api/tipo-monedas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoMoneda)))
            .andExpect(status().isOk());

        // Validate the TipoMoneda in the database
        List<TipoMoneda> tipoMonedaList = tipoMonedaRepository.findAll();
        assertThat(tipoMonedaList).hasSize(databaseSizeBeforeUpdate);
        TipoMoneda testTipoMoneda = tipoMonedaList.get(tipoMonedaList.size() - 1);
        assertThat(testTipoMoneda.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoMoneda() throws Exception {
        int databaseSizeBeforeUpdate = tipoMonedaRepository.findAll().size();

        // Create the TipoMoneda

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMonedaMockMvc.perform(put("/api/tipo-monedas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoMoneda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMoneda in the database
        List<TipoMoneda> tipoMonedaList = tipoMonedaRepository.findAll();
        assertThat(tipoMonedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoMoneda() throws Exception {
        // Initialize the database
        tipoMonedaRepository.saveAndFlush(tipoMoneda);

        int databaseSizeBeforeDelete = tipoMonedaRepository.findAll().size();

        // Delete the tipoMoneda
        restTipoMonedaMockMvc.perform(delete("/api/tipo-monedas/{id}", tipoMoneda.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoMoneda> tipoMonedaList = tipoMonedaRepository.findAll();
        assertThat(tipoMonedaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
