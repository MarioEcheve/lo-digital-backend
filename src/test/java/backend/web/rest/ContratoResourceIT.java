package backend.web.rest;

import backend.BackendApp;
import backend.domain.Contrato;
import backend.repository.ContratoRepository;
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
 * Integration tests for the {@link ContratoResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
public class ContratoResourceIT {

    private static final Instant DEFAULT_FECHA_INICIO_SERVICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO_SERVICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_TERMINO_SERVICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_TERMINO_SERVICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_TERMINO_ACCESO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_TERMINO_ACCESO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVACIONES_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_OTRO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_OTRO = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDAD_OTRA = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD_OTRA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_TERMINO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_TERMINO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO_SECUNDARIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO_SECUNDARIO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CONTACTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_DEPENDENCIA_CONTRATISTA = 1;
    private static final Integer UPDATED_ID_DEPENDENCIA_CONTRATISTA = 2;

    private static final Boolean DEFAULT_CREA_LIBRO_ADMIN_MAN = false;
    private static final Boolean UPDATED_CREA_LIBRO_ADMIN_MAN = true;

    private static final Boolean DEFAULT_CREA_LIBRO_ADMIN_CON = false;
    private static final Boolean UPDATED_CREA_LIBRO_ADMIN_CON = true;

    private static final Boolean DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_MAN = false;
    private static final Boolean UPDATED_ACTUALIZAR_CONTRATO_ADMIN_MAN = true;

    private static final Boolean DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_CON = false;
    private static final Boolean UPDATED_ACTUALIZAR_CONTRATO_ADMIN_CON = true;

    @Autowired
    private ContratoRepository contratoRepository;

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

    private MockMvc restContratoMockMvc;

    private Contrato contrato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContratoResource contratoResource = new ContratoResource(contratoRepository);
        this.restContratoMockMvc = MockMvcBuilders.standaloneSetup(contratoResource)
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
    public static Contrato createEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .fechaInicioServicio(DEFAULT_FECHA_INICIO_SERVICIO)
            .fechaTerminoServicio(DEFAULT_FECHA_TERMINO_SERVICIO)
            .fechaTerminoAcceso(DEFAULT_FECHA_TERMINO_ACCESO)
            .observacionesServicio(DEFAULT_OBSERVACIONES_SERVICIO)
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .tipoOtro(DEFAULT_TIPO_OTRO)
            .modalidadOtra(DEFAULT_MODALIDAD_OTRA)
            .direccion(DEFAULT_DIRECCION)
            .monto(DEFAULT_MONTO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaTermino(DEFAULT_FECHA_TERMINO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .nombreContacto(DEFAULT_NOMBRE_CONTACTO)
            .cargo(DEFAULT_CARGO)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO)
            .telefonoContactoSecundario(DEFAULT_TELEFONO_CONTACTO_SECUNDARIO)
            .emailContacto(DEFAULT_EMAIL_CONTACTO)
            .idDependenciaContratista(DEFAULT_ID_DEPENDENCIA_CONTRATISTA)
            .creaLibroAdminMan(DEFAULT_CREA_LIBRO_ADMIN_MAN)
            .creaLibroAdminCon(DEFAULT_CREA_LIBRO_ADMIN_CON)
            .actualizarContratoAdminMan(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_MAN)
            .actualizarContratoAdminCon(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_CON);
        return contrato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createUpdatedEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .fechaInicioServicio(UPDATED_FECHA_INICIO_SERVICIO)
            .fechaTerminoServicio(UPDATED_FECHA_TERMINO_SERVICIO)
            .fechaTerminoAcceso(UPDATED_FECHA_TERMINO_ACCESO)
            .observacionesServicio(UPDATED_OBSERVACIONES_SERVICIO)
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoOtro(UPDATED_TIPO_OTRO)
            .modalidadOtra(UPDATED_MODALIDAD_OTRA)
            .direccion(UPDATED_DIRECCION)
            .monto(UPDATED_MONTO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTermino(UPDATED_FECHA_TERMINO)
            .observaciones(UPDATED_OBSERVACIONES)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .cargo(UPDATED_CARGO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .telefonoContactoSecundario(UPDATED_TELEFONO_CONTACTO_SECUNDARIO)
            .emailContacto(UPDATED_EMAIL_CONTACTO)
            .idDependenciaContratista(UPDATED_ID_DEPENDENCIA_CONTRATISTA)
            .creaLibroAdminMan(UPDATED_CREA_LIBRO_ADMIN_MAN)
            .creaLibroAdminCon(UPDATED_CREA_LIBRO_ADMIN_CON)
            .actualizarContratoAdminMan(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_MAN)
            .actualizarContratoAdminCon(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_CON);
        return contrato;
    }

    @BeforeEach
    public void initTest() {
        contrato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrato() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getFechaInicioServicio()).isEqualTo(DEFAULT_FECHA_INICIO_SERVICIO);
        assertThat(testContrato.getFechaTerminoServicio()).isEqualTo(DEFAULT_FECHA_TERMINO_SERVICIO);
        assertThat(testContrato.getFechaTerminoAcceso()).isEqualTo(DEFAULT_FECHA_TERMINO_ACCESO);
        assertThat(testContrato.getObservacionesServicio()).isEqualTo(DEFAULT_OBSERVACIONES_SERVICIO);
        assertThat(testContrato.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testContrato.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContrato.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testContrato.getTipoOtro()).isEqualTo(DEFAULT_TIPO_OTRO);
        assertThat(testContrato.getModalidadOtra()).isEqualTo(DEFAULT_MODALIDAD_OTRA);
        assertThat(testContrato.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testContrato.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testContrato.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testContrato.getFechaTermino()).isEqualTo(DEFAULT_FECHA_TERMINO);
        assertThat(testContrato.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testContrato.getNombreContacto()).isEqualTo(DEFAULT_NOMBRE_CONTACTO);
        assertThat(testContrato.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testContrato.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testContrato.getTelefonoContactoSecundario()).isEqualTo(DEFAULT_TELEFONO_CONTACTO_SECUNDARIO);
        assertThat(testContrato.getEmailContacto()).isEqualTo(DEFAULT_EMAIL_CONTACTO);
        assertThat(testContrato.getIdDependenciaContratista()).isEqualTo(DEFAULT_ID_DEPENDENCIA_CONTRATISTA);
        assertThat(testContrato.isCreaLibroAdminMan()).isEqualTo(DEFAULT_CREA_LIBRO_ADMIN_MAN);
        assertThat(testContrato.isCreaLibroAdminCon()).isEqualTo(DEFAULT_CREA_LIBRO_ADMIN_CON);
        assertThat(testContrato.isActualizarContratoAdminMan()).isEqualTo(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_MAN);
        assertThat(testContrato.isActualizarContratoAdminCon()).isEqualTo(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_CON);
    }

    @Test
    @Transactional
    public void createContratoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato with an existing ID
        contrato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setCodigo(null);

        // Create the Contrato, which fails.

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setNombre(null);

        // Create the Contrato, which fails.

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setDireccion(null);

        // Create the Contrato, which fails.

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContratoes() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get all the contratoList
        restContratoMockMvc.perform(get("/api/contratoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicioServicio").value(hasItem(DEFAULT_FECHA_INICIO_SERVICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaTerminoServicio").value(hasItem(DEFAULT_FECHA_TERMINO_SERVICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaTerminoAcceso").value(hasItem(DEFAULT_FECHA_TERMINO_ACCESO.toString())))
            .andExpect(jsonPath("$.[*].observacionesServicio").value(hasItem(DEFAULT_OBSERVACIONES_SERVICIO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipoOtro").value(hasItem(DEFAULT_TIPO_OTRO)))
            .andExpect(jsonPath("$.[*].modalidadOtra").value(hasItem(DEFAULT_MODALIDAD_OTRA)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaTermino").value(hasItem(DEFAULT_FECHA_TERMINO.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)))
            .andExpect(jsonPath("$.[*].telefonoContactoSecundario").value(hasItem(DEFAULT_TELEFONO_CONTACTO_SECUNDARIO)))
            .andExpect(jsonPath("$.[*].emailContacto").value(hasItem(DEFAULT_EMAIL_CONTACTO)))
            .andExpect(jsonPath("$.[*].idDependenciaContratista").value(hasItem(DEFAULT_ID_DEPENDENCIA_CONTRATISTA)))
            .andExpect(jsonPath("$.[*].creaLibroAdminMan").value(hasItem(DEFAULT_CREA_LIBRO_ADMIN_MAN.booleanValue())))
            .andExpect(jsonPath("$.[*].creaLibroAdminCon").value(hasItem(DEFAULT_CREA_LIBRO_ADMIN_CON.booleanValue())))
            .andExpect(jsonPath("$.[*].actualizarContratoAdminMan").value(hasItem(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_MAN.booleanValue())))
            .andExpect(jsonPath("$.[*].actualizarContratoAdminCon").value(hasItem(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_CON.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicioServicio").value(DEFAULT_FECHA_INICIO_SERVICIO.toString()))
            .andExpect(jsonPath("$.fechaTerminoServicio").value(DEFAULT_FECHA_TERMINO_SERVICIO.toString()))
            .andExpect(jsonPath("$.fechaTerminoAcceso").value(DEFAULT_FECHA_TERMINO_ACCESO.toString()))
            .andExpect(jsonPath("$.observacionesServicio").value(DEFAULT_OBSERVACIONES_SERVICIO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.tipoOtro").value(DEFAULT_TIPO_OTRO))
            .andExpect(jsonPath("$.modalidadOtra").value(DEFAULT_MODALIDAD_OTRA))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaTermino").value(DEFAULT_FECHA_TERMINO.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.nombreContacto").value(DEFAULT_NOMBRE_CONTACTO))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO))
            .andExpect(jsonPath("$.telefonoContactoSecundario").value(DEFAULT_TELEFONO_CONTACTO_SECUNDARIO))
            .andExpect(jsonPath("$.emailContacto").value(DEFAULT_EMAIL_CONTACTO))
            .andExpect(jsonPath("$.idDependenciaContratista").value(DEFAULT_ID_DEPENDENCIA_CONTRATISTA))
            .andExpect(jsonPath("$.creaLibroAdminMan").value(DEFAULT_CREA_LIBRO_ADMIN_MAN.booleanValue()))
            .andExpect(jsonPath("$.creaLibroAdminCon").value(DEFAULT_CREA_LIBRO_ADMIN_CON.booleanValue()))
            .andExpect(jsonPath("$.actualizarContratoAdminMan").value(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_MAN.booleanValue()))
            .andExpect(jsonPath("$.actualizarContratoAdminCon").value(DEFAULT_ACTUALIZAR_CONTRATO_ADMIN_CON.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContrato() throws Exception {
        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato
        Contrato updatedContrato = contratoRepository.findById(contrato.getId()).get();
        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
        em.detach(updatedContrato);
        updatedContrato
            .fechaInicioServicio(UPDATED_FECHA_INICIO_SERVICIO)
            .fechaTerminoServicio(UPDATED_FECHA_TERMINO_SERVICIO)
            .fechaTerminoAcceso(UPDATED_FECHA_TERMINO_ACCESO)
            .observacionesServicio(UPDATED_OBSERVACIONES_SERVICIO)
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoOtro(UPDATED_TIPO_OTRO)
            .modalidadOtra(UPDATED_MODALIDAD_OTRA)
            .direccion(UPDATED_DIRECCION)
            .monto(UPDATED_MONTO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTermino(UPDATED_FECHA_TERMINO)
            .observaciones(UPDATED_OBSERVACIONES)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .cargo(UPDATED_CARGO)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .telefonoContactoSecundario(UPDATED_TELEFONO_CONTACTO_SECUNDARIO)
            .emailContacto(UPDATED_EMAIL_CONTACTO)
            .idDependenciaContratista(UPDATED_ID_DEPENDENCIA_CONTRATISTA)
            .creaLibroAdminMan(UPDATED_CREA_LIBRO_ADMIN_MAN)
            .creaLibroAdminCon(UPDATED_CREA_LIBRO_ADMIN_CON)
            .actualizarContratoAdminMan(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_MAN)
            .actualizarContratoAdminCon(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_CON);

        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrato)))
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getFechaInicioServicio()).isEqualTo(UPDATED_FECHA_INICIO_SERVICIO);
        assertThat(testContrato.getFechaTerminoServicio()).isEqualTo(UPDATED_FECHA_TERMINO_SERVICIO);
        assertThat(testContrato.getFechaTerminoAcceso()).isEqualTo(UPDATED_FECHA_TERMINO_ACCESO);
        assertThat(testContrato.getObservacionesServicio()).isEqualTo(UPDATED_OBSERVACIONES_SERVICIO);
        assertThat(testContrato.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testContrato.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContrato.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testContrato.getTipoOtro()).isEqualTo(UPDATED_TIPO_OTRO);
        assertThat(testContrato.getModalidadOtra()).isEqualTo(UPDATED_MODALIDAD_OTRA);
        assertThat(testContrato.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testContrato.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testContrato.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testContrato.getFechaTermino()).isEqualTo(UPDATED_FECHA_TERMINO);
        assertThat(testContrato.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testContrato.getNombreContacto()).isEqualTo(UPDATED_NOMBRE_CONTACTO);
        assertThat(testContrato.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testContrato.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testContrato.getTelefonoContactoSecundario()).isEqualTo(UPDATED_TELEFONO_CONTACTO_SECUNDARIO);
        assertThat(testContrato.getEmailContacto()).isEqualTo(UPDATED_EMAIL_CONTACTO);
        assertThat(testContrato.getIdDependenciaContratista()).isEqualTo(UPDATED_ID_DEPENDENCIA_CONTRATISTA);
        assertThat(testContrato.isCreaLibroAdminMan()).isEqualTo(UPDATED_CREA_LIBRO_ADMIN_MAN);
        assertThat(testContrato.isCreaLibroAdminCon()).isEqualTo(UPDATED_CREA_LIBRO_ADMIN_CON);
        assertThat(testContrato.isActualizarContratoAdminMan()).isEqualTo(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_MAN);
        assertThat(testContrato.isActualizarContratoAdminCon()).isEqualTo(UPDATED_ACTUALIZAR_CONTRATO_ADMIN_CON);
    }

    @Test
    @Transactional
    public void updateNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Create the Contrato

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeDelete = contratoRepository.findAll().size();

        // Delete the contrato
        restContratoMockMvc.perform(delete("/api/contratoes/{id}", contrato.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
