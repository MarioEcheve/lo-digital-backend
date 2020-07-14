package backend.web.rest;

import backend.BackendApp;
import backend.domain.Folio;
import backend.repository.FolioRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FolioResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class FolioResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_CREADOR = 1;
    private static final Integer UPDATED_ID_USUARIO_CREADOR = 2;

    private static final Integer DEFAULT_ID_USUARIO_FIRMA = 1;
    private static final Integer UPDATED_ID_USUARIO_FIRMA = 2;

    private static final Integer DEFAULT_ID_USUARIO_LECTURA = 1;
    private static final Integer UPDATED_ID_USUARIO_LECTURA = 2;

    private static final Integer DEFAULT_NUMERO_FOLIO = 1;
    private static final Integer UPDATED_NUMERO_FOLIO = 2;

    private static final Boolean DEFAULT_REQUIERE_RESPUESTA = false;
    private static final Boolean UPDATED_REQUIERE_RESPUESTA = true;

    private static final Instant DEFAULT_FECHA_REQUERIDA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REQUERIDA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO_LECTURA = false;
    private static final Boolean UPDATED_ESTADO_LECTURA = true;

    private static final Boolean DEFAULT_ESTADO_FOLIO = false;
    private static final Boolean UPDATED_ESTADO_FOLIO = true;

    private static final Boolean DEFAULT_ENTIDAD_CREACION = false;
    private static final Boolean UPDATED_ENTIDAD_CREACION = true;

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_IDLIBRO_RELACIONADO = 1;
    private static final Integer UPDATED_IDLIBRO_RELACIONADO = 2;

    private static final Integer DEFAULT_ID_FOLIO_RELACIONADO = 1;
    private static final Integer UPDATED_ID_FOLIO_RELACIONADO = 2;

    private static final Integer DEFAULT_ID_FOLIO_RESPUESTA = 1;
    private static final Integer UPDATED_ID_FOLIO_RESPUESTA = 2;

    private static final Instant DEFAULT_FECHA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIRMA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIRMA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_LECTURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_LECTURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ASUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_ANOTACION = "AAAAAAAAAA";
    private static final String UPDATED_ANOTACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PDF_FIRMADO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF_FIRMADO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_FIRMADO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_FIRMADO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PDF_LECTURA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF_LECTURA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_LECTURA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_LECTURA_CONTENT_TYPE = "image/png";

    @Autowired
    private FolioRepository folioRepository;

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

    private MockMvc restFolioMockMvc;

    private Folio folio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FolioResource folioResource = new FolioResource(folioRepository);
        this.restFolioMockMvc = MockMvcBuilders.standaloneSetup(folioResource)
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
    public static Folio createEntity(EntityManager em) {
        Folio folio = new Folio()
            .idUsuarioCreador(DEFAULT_ID_USUARIO_CREADOR)
            .idUsuarioFirma(DEFAULT_ID_USUARIO_FIRMA)
            .idUsuarioLectura(DEFAULT_ID_USUARIO_LECTURA)
            .numeroFolio(DEFAULT_NUMERO_FOLIO)
            .requiereRespuesta(DEFAULT_REQUIERE_RESPUESTA)
            .fechaRequerida(DEFAULT_FECHA_REQUERIDA)
            .estadoLectura(DEFAULT_ESTADO_LECTURA)
            .estadoFolio(DEFAULT_ESTADO_FOLIO)
            .entidadCreacion(DEFAULT_ENTIDAD_CREACION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .idlibroRelacionado(DEFAULT_IDLIBRO_RELACIONADO)
            .idFolioRelacionado(DEFAULT_ID_FOLIO_RELACIONADO)
            .idFolioRespuesta(DEFAULT_ID_FOLIO_RESPUESTA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .fechaFirma(DEFAULT_FECHA_FIRMA)
            .fechaLectura(DEFAULT_FECHA_LECTURA)
            .asunto(DEFAULT_ASUNTO)
            .anotacion(DEFAULT_ANOTACION)
            .pdfFirmado(DEFAULT_PDF_FIRMADO)
            .pdfFirmadoContentType(DEFAULT_PDF_FIRMADO_CONTENT_TYPE)
            .pdfLectura(DEFAULT_PDF_LECTURA)
            .pdfLecturaContentType(DEFAULT_PDF_LECTURA_CONTENT_TYPE);
        return folio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Folio createUpdatedEntity(EntityManager em) {
        Folio folio = new Folio()
            .idUsuarioCreador(UPDATED_ID_USUARIO_CREADOR)
            .idUsuarioFirma(UPDATED_ID_USUARIO_FIRMA)
            .idUsuarioLectura(UPDATED_ID_USUARIO_LECTURA)
            .numeroFolio(UPDATED_NUMERO_FOLIO)
            .requiereRespuesta(UPDATED_REQUIERE_RESPUESTA)
            .fechaRequerida(UPDATED_FECHA_REQUERIDA)
            .estadoLectura(UPDATED_ESTADO_LECTURA)
            .estadoFolio(UPDATED_ESTADO_FOLIO)
            .entidadCreacion(UPDATED_ENTIDAD_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .idlibroRelacionado(UPDATED_IDLIBRO_RELACIONADO)
            .idFolioRelacionado(UPDATED_ID_FOLIO_RELACIONADO)
            .idFolioRespuesta(UPDATED_ID_FOLIO_RESPUESTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .fechaLectura(UPDATED_FECHA_LECTURA)
            .asunto(UPDATED_ASUNTO)
            .anotacion(UPDATED_ANOTACION)
            .pdfFirmado(UPDATED_PDF_FIRMADO)
            .pdfFirmadoContentType(UPDATED_PDF_FIRMADO_CONTENT_TYPE)
            .pdfLectura(UPDATED_PDF_LECTURA)
            .pdfLecturaContentType(UPDATED_PDF_LECTURA_CONTENT_TYPE);
        return folio;
    }

    @BeforeEach
    public void initTest() {
        folio = createEntity(em);
    }

    @Test
    @Transactional
    public void createFolio() throws Exception {
        int databaseSizeBeforeCreate = folioRepository.findAll().size();

        // Create the Folio
        restFolioMockMvc.perform(post("/api/folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folio)))
            .andExpect(status().isCreated());

        // Validate the Folio in the database
        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeCreate + 1);
        Folio testFolio = folioList.get(folioList.size() - 1);
        assertThat(testFolio.getIdUsuarioCreador()).isEqualTo(DEFAULT_ID_USUARIO_CREADOR);
        assertThat(testFolio.getIdUsuarioFirma()).isEqualTo(DEFAULT_ID_USUARIO_FIRMA);
        assertThat(testFolio.getIdUsuarioLectura()).isEqualTo(DEFAULT_ID_USUARIO_LECTURA);
        assertThat(testFolio.getNumeroFolio()).isEqualTo(DEFAULT_NUMERO_FOLIO);
        assertThat(testFolio.isRequiereRespuesta()).isEqualTo(DEFAULT_REQUIERE_RESPUESTA);
        assertThat(testFolio.getFechaRequerida()).isEqualTo(DEFAULT_FECHA_REQUERIDA);
        assertThat(testFolio.isEstadoLectura()).isEqualTo(DEFAULT_ESTADO_LECTURA);
        assertThat(testFolio.isEstadoFolio()).isEqualTo(DEFAULT_ESTADO_FOLIO);
        assertThat(testFolio.isEntidadCreacion()).isEqualTo(DEFAULT_ENTIDAD_CREACION);
        assertThat(testFolio.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testFolio.getIdlibroRelacionado()).isEqualTo(DEFAULT_IDLIBRO_RELACIONADO);
        assertThat(testFolio.getIdFolioRelacionado()).isEqualTo(DEFAULT_ID_FOLIO_RELACIONADO);
        assertThat(testFolio.getIdFolioRespuesta()).isEqualTo(DEFAULT_ID_FOLIO_RESPUESTA);
        assertThat(testFolio.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testFolio.getFechaFirma()).isEqualTo(DEFAULT_FECHA_FIRMA);
        assertThat(testFolio.getFechaLectura()).isEqualTo(DEFAULT_FECHA_LECTURA);
        assertThat(testFolio.getAsunto()).isEqualTo(DEFAULT_ASUNTO);
        assertThat(testFolio.getAnotacion()).isEqualTo(DEFAULT_ANOTACION);
        assertThat(testFolio.getPdfFirmado()).isEqualTo(DEFAULT_PDF_FIRMADO);
        assertThat(testFolio.getPdfFirmadoContentType()).isEqualTo(DEFAULT_PDF_FIRMADO_CONTENT_TYPE);
        assertThat(testFolio.getPdfLectura()).isEqualTo(DEFAULT_PDF_LECTURA);
        assertThat(testFolio.getPdfLecturaContentType()).isEqualTo(DEFAULT_PDF_LECTURA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFolioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = folioRepository.findAll().size();

        // Create the Folio with an existing ID
        folio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFolioMockMvc.perform(post("/api/folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folio)))
            .andExpect(status().isBadRequest());

        // Validate the Folio in the database
        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAsuntoIsRequired() throws Exception {
        int databaseSizeBeforeTest = folioRepository.findAll().size();
        // set the field null
        folio.setAsunto(null);

        // Create the Folio, which fails.

        restFolioMockMvc.perform(post("/api/folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folio)))
            .andExpect(status().isBadRequest());

        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFolios() throws Exception {
        // Initialize the database
        folioRepository.saveAndFlush(folio);

        // Get all the folioList
        restFolioMockMvc.perform(get("/api/folios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(folio.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioCreador").value(hasItem(DEFAULT_ID_USUARIO_CREADOR)))
            .andExpect(jsonPath("$.[*].idUsuarioFirma").value(hasItem(DEFAULT_ID_USUARIO_FIRMA)))
            .andExpect(jsonPath("$.[*].idUsuarioLectura").value(hasItem(DEFAULT_ID_USUARIO_LECTURA)))
            .andExpect(jsonPath("$.[*].numeroFolio").value(hasItem(DEFAULT_NUMERO_FOLIO)))
            .andExpect(jsonPath("$.[*].requiereRespuesta").value(hasItem(DEFAULT_REQUIERE_RESPUESTA.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaRequerida").value(hasItem(DEFAULT_FECHA_REQUERIDA.toString())))
            .andExpect(jsonPath("$.[*].estadoLectura").value(hasItem(DEFAULT_ESTADO_LECTURA.booleanValue())))
            .andExpect(jsonPath("$.[*].estadoFolio").value(hasItem(DEFAULT_ESTADO_FOLIO.booleanValue())))
            .andExpect(jsonPath("$.[*].entidadCreacion").value(hasItem(DEFAULT_ENTIDAD_CREACION.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].idlibroRelacionado").value(hasItem(DEFAULT_IDLIBRO_RELACIONADO)))
            .andExpect(jsonPath("$.[*].idFolioRelacionado").value(hasItem(DEFAULT_ID_FOLIO_RELACIONADO)))
            .andExpect(jsonPath("$.[*].idFolioRespuesta").value(hasItem(DEFAULT_ID_FOLIO_RESPUESTA)))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].fechaFirma").value(hasItem(DEFAULT_FECHA_FIRMA.toString())))
            .andExpect(jsonPath("$.[*].fechaLectura").value(hasItem(DEFAULT_FECHA_LECTURA.toString())))
            .andExpect(jsonPath("$.[*].asunto").value(hasItem(DEFAULT_ASUNTO)))
            .andExpect(jsonPath("$.[*].anotacion").value(hasItem(DEFAULT_ANOTACION)))
            .andExpect(jsonPath("$.[*].pdfFirmadoContentType").value(hasItem(DEFAULT_PDF_FIRMADO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdfFirmado").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF_FIRMADO))))
            .andExpect(jsonPath("$.[*].pdfLecturaContentType").value(hasItem(DEFAULT_PDF_LECTURA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdfLectura").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF_LECTURA))));
    }
    
    @Test
    @Transactional
    public void getFolio() throws Exception {
        // Initialize the database
        folioRepository.saveAndFlush(folio);

        // Get the folio
        restFolioMockMvc.perform(get("/api/folios/{id}", folio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(folio.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioCreador").value(DEFAULT_ID_USUARIO_CREADOR))
            .andExpect(jsonPath("$.idUsuarioFirma").value(DEFAULT_ID_USUARIO_FIRMA))
            .andExpect(jsonPath("$.idUsuarioLectura").value(DEFAULT_ID_USUARIO_LECTURA))
            .andExpect(jsonPath("$.numeroFolio").value(DEFAULT_NUMERO_FOLIO))
            .andExpect(jsonPath("$.requiereRespuesta").value(DEFAULT_REQUIERE_RESPUESTA.booleanValue()))
            .andExpect(jsonPath("$.fechaRequerida").value(DEFAULT_FECHA_REQUERIDA.toString()))
            .andExpect(jsonPath("$.estadoLectura").value(DEFAULT_ESTADO_LECTURA.booleanValue()))
            .andExpect(jsonPath("$.estadoFolio").value(DEFAULT_ESTADO_FOLIO.booleanValue()))
            .andExpect(jsonPath("$.entidadCreacion").value(DEFAULT_ENTIDAD_CREACION.booleanValue()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.idlibroRelacionado").value(DEFAULT_IDLIBRO_RELACIONADO))
            .andExpect(jsonPath("$.idFolioRelacionado").value(DEFAULT_ID_FOLIO_RELACIONADO))
            .andExpect(jsonPath("$.idFolioRespuesta").value(DEFAULT_ID_FOLIO_RESPUESTA))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.fechaFirma").value(DEFAULT_FECHA_FIRMA.toString()))
            .andExpect(jsonPath("$.fechaLectura").value(DEFAULT_FECHA_LECTURA.toString()))
            .andExpect(jsonPath("$.asunto").value(DEFAULT_ASUNTO))
            .andExpect(jsonPath("$.anotacion").value(DEFAULT_ANOTACION))
            .andExpect(jsonPath("$.pdfFirmadoContentType").value(DEFAULT_PDF_FIRMADO_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdfFirmado").value(Base64Utils.encodeToString(DEFAULT_PDF_FIRMADO)))
            .andExpect(jsonPath("$.pdfLecturaContentType").value(DEFAULT_PDF_LECTURA_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdfLectura").value(Base64Utils.encodeToString(DEFAULT_PDF_LECTURA)));
    }

    @Test
    @Transactional
    public void getNonExistingFolio() throws Exception {
        // Get the folio
        restFolioMockMvc.perform(get("/api/folios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFolio() throws Exception {
        // Initialize the database
        folioRepository.saveAndFlush(folio);

        int databaseSizeBeforeUpdate = folioRepository.findAll().size();

        // Update the folio
        Folio updatedFolio = folioRepository.findById(folio.getId()).get();
        // Disconnect from session so that the updates on updatedFolio are not directly saved in db
        em.detach(updatedFolio);
        updatedFolio
            .idUsuarioCreador(UPDATED_ID_USUARIO_CREADOR)
            .idUsuarioFirma(UPDATED_ID_USUARIO_FIRMA)
            .idUsuarioLectura(UPDATED_ID_USUARIO_LECTURA)
            .numeroFolio(UPDATED_NUMERO_FOLIO)
            .requiereRespuesta(UPDATED_REQUIERE_RESPUESTA)
            .fechaRequerida(UPDATED_FECHA_REQUERIDA)
            .estadoLectura(UPDATED_ESTADO_LECTURA)
            .estadoFolio(UPDATED_ESTADO_FOLIO)
            .entidadCreacion(UPDATED_ENTIDAD_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .idlibroRelacionado(UPDATED_IDLIBRO_RELACIONADO)
            .idFolioRelacionado(UPDATED_ID_FOLIO_RELACIONADO)
            .idFolioRespuesta(UPDATED_ID_FOLIO_RESPUESTA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .fechaFirma(UPDATED_FECHA_FIRMA)
            .fechaLectura(UPDATED_FECHA_LECTURA)
            .asunto(UPDATED_ASUNTO)
            .anotacion(UPDATED_ANOTACION)
            .pdfFirmado(UPDATED_PDF_FIRMADO)
            .pdfFirmadoContentType(UPDATED_PDF_FIRMADO_CONTENT_TYPE)
            .pdfLectura(UPDATED_PDF_LECTURA)
            .pdfLecturaContentType(UPDATED_PDF_LECTURA_CONTENT_TYPE);

        restFolioMockMvc.perform(put("/api/folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFolio)))
            .andExpect(status().isOk());

        // Validate the Folio in the database
        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeUpdate);
        Folio testFolio = folioList.get(folioList.size() - 1);
        assertThat(testFolio.getIdUsuarioCreador()).isEqualTo(UPDATED_ID_USUARIO_CREADOR);
        assertThat(testFolio.getIdUsuarioFirma()).isEqualTo(UPDATED_ID_USUARIO_FIRMA);
        assertThat(testFolio.getIdUsuarioLectura()).isEqualTo(UPDATED_ID_USUARIO_LECTURA);
        assertThat(testFolio.getNumeroFolio()).isEqualTo(UPDATED_NUMERO_FOLIO);
        assertThat(testFolio.isRequiereRespuesta()).isEqualTo(UPDATED_REQUIERE_RESPUESTA);
        assertThat(testFolio.getFechaRequerida()).isEqualTo(UPDATED_FECHA_REQUERIDA);
        assertThat(testFolio.isEstadoLectura()).isEqualTo(UPDATED_ESTADO_LECTURA);
        assertThat(testFolio.isEstadoFolio()).isEqualTo(UPDATED_ESTADO_FOLIO);
        assertThat(testFolio.isEntidadCreacion()).isEqualTo(UPDATED_ENTIDAD_CREACION);
        assertThat(testFolio.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testFolio.getIdlibroRelacionado()).isEqualTo(UPDATED_IDLIBRO_RELACIONADO);
        assertThat(testFolio.getIdFolioRelacionado()).isEqualTo(UPDATED_ID_FOLIO_RELACIONADO);
        assertThat(testFolio.getIdFolioRespuesta()).isEqualTo(UPDATED_ID_FOLIO_RESPUESTA);
        assertThat(testFolio.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testFolio.getFechaFirma()).isEqualTo(UPDATED_FECHA_FIRMA);
        assertThat(testFolio.getFechaLectura()).isEqualTo(UPDATED_FECHA_LECTURA);
        assertThat(testFolio.getAsunto()).isEqualTo(UPDATED_ASUNTO);
        assertThat(testFolio.getAnotacion()).isEqualTo(UPDATED_ANOTACION);
        assertThat(testFolio.getPdfFirmado()).isEqualTo(UPDATED_PDF_FIRMADO);
        assertThat(testFolio.getPdfFirmadoContentType()).isEqualTo(UPDATED_PDF_FIRMADO_CONTENT_TYPE);
        assertThat(testFolio.getPdfLectura()).isEqualTo(UPDATED_PDF_LECTURA);
        assertThat(testFolio.getPdfLecturaContentType()).isEqualTo(UPDATED_PDF_LECTURA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFolio() throws Exception {
        int databaseSizeBeforeUpdate = folioRepository.findAll().size();

        // Create the Folio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFolioMockMvc.perform(put("/api/folios")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(folio)))
            .andExpect(status().isBadRequest());

        // Validate the Folio in the database
        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFolio() throws Exception {
        // Initialize the database
        folioRepository.saveAndFlush(folio);

        int databaseSizeBeforeDelete = folioRepository.findAll().size();

        // Delete the folio
        restFolioMockMvc.perform(delete("/api/folios/{id}", folio.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Folio> folioList = folioRepository.findAll();
        assertThat(folioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
