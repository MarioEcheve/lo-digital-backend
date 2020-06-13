package backend.web.rest;

import backend.BackendApp;
import backend.domain.TipoContrato;
import backend.repository.TipoContratoRepository;
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
 * Integration tests for the {@link TipoContratoResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class TipoContratoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoContratoRepository tipoContratoRepository;

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

    private MockMvc restTipoContratoMockMvc;

    private TipoContrato tipoContrato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoContratoResource tipoContratoResource = new TipoContratoResource(tipoContratoRepository);
        this.restTipoContratoMockMvc = MockMvcBuilders.standaloneSetup(tipoContratoResource)
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
    public static TipoContrato createEntity(EntityManager em) {
        TipoContrato tipoContrato = new TipoContrato()
            .nombre(DEFAULT_NOMBRE);
        return tipoContrato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoContrato createUpdatedEntity(EntityManager em) {
        TipoContrato tipoContrato = new TipoContrato()
            .nombre(UPDATED_NOMBRE);
        return tipoContrato;
    }

    @BeforeEach
    public void initTest() {
        tipoContrato = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoContrato() throws Exception {
        int databaseSizeBeforeCreate = tipoContratoRepository.findAll().size();

        // Create the TipoContrato
        restTipoContratoMockMvc.perform(post("/api/tipo-contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoContrato)))
            .andExpect(status().isCreated());

        // Validate the TipoContrato in the database
        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoContrato testTipoContrato = tipoContratoList.get(tipoContratoList.size() - 1);
        assertThat(testTipoContrato.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoContratoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoContratoRepository.findAll().size();

        // Create the TipoContrato with an existing ID
        tipoContrato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoContratoMockMvc.perform(post("/api/tipo-contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoContrato)))
            .andExpect(status().isBadRequest());

        // Validate the TipoContrato in the database
        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoContratoRepository.findAll().size();
        // set the field null
        tipoContrato.setNombre(null);

        // Create the TipoContrato, which fails.

        restTipoContratoMockMvc.perform(post("/api/tipo-contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoContrato)))
            .andExpect(status().isBadRequest());

        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoContratoes() throws Exception {
        // Initialize the database
        tipoContratoRepository.saveAndFlush(tipoContrato);

        // Get all the tipoContratoList
        restTipoContratoMockMvc.perform(get("/api/tipo-contratoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoContrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getTipoContrato() throws Exception {
        // Initialize the database
        tipoContratoRepository.saveAndFlush(tipoContrato);

        // Get the tipoContrato
        restTipoContratoMockMvc.perform(get("/api/tipo-contratoes/{id}", tipoContrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoContrato.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingTipoContrato() throws Exception {
        // Get the tipoContrato
        restTipoContratoMockMvc.perform(get("/api/tipo-contratoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoContrato() throws Exception {
        // Initialize the database
        tipoContratoRepository.saveAndFlush(tipoContrato);

        int databaseSizeBeforeUpdate = tipoContratoRepository.findAll().size();

        // Update the tipoContrato
        TipoContrato updatedTipoContrato = tipoContratoRepository.findById(tipoContrato.getId()).get();
        // Disconnect from session so that the updates on updatedTipoContrato are not directly saved in db
        em.detach(updatedTipoContrato);
        updatedTipoContrato
            .nombre(UPDATED_NOMBRE);

        restTipoContratoMockMvc.perform(put("/api/tipo-contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoContrato)))
            .andExpect(status().isOk());

        // Validate the TipoContrato in the database
        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeUpdate);
        TipoContrato testTipoContrato = tipoContratoList.get(tipoContratoList.size() - 1);
        assertThat(testTipoContrato.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoContrato() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratoRepository.findAll().size();

        // Create the TipoContrato

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoContratoMockMvc.perform(put("/api/tipo-contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoContrato)))
            .andExpect(status().isBadRequest());

        // Validate the TipoContrato in the database
        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoContrato() throws Exception {
        // Initialize the database
        tipoContratoRepository.saveAndFlush(tipoContrato);

        int databaseSizeBeforeDelete = tipoContratoRepository.findAll().size();

        // Delete the tipoContrato
        restTipoContratoMockMvc.perform(delete("/api/tipo-contratoes/{id}", tipoContrato.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoContrato> tipoContratoList = tipoContratoRepository.findAll();
        assertThat(tipoContratoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
