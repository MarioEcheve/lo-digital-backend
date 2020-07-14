package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contrato.
 */
@Entity
@Table(name = "contrato")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha_inicio_servicio")
    private Instant fechaInicioServicio;

    @Column(name = "fecha_termino_servicio")
    private Instant fechaTerminoServicio;

    @Column(name = "fecha_termino_acceso")
    private Instant fechaTerminoAcceso;

    @Size(max = 250)
    @Column(name = "observaciones_servicio", length = 250)
    private String observacionesServicio;

    @NotNull
    @Size(max = 20)
    @Column(name = "codigo", length = 20, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 60)
    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Size(max = 25)
    @Column(name = "tipo_otro", length = 25)
    private String tipoOtro;

    @Size(max = 25)
    @Column(name = "modalidad_otra", length = 25)
    private String modalidadOtra;

    @NotNull
    @Size(max = 100)
    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_termino")
    private Instant fechaTermino;

    @Size(max = 200)
    @Column(name = "observaciones", length = 200)
    private String observaciones;

    @Size(max = 50)
    @Column(name = "nombre_contacto", length = 50)
    private String nombreContacto;

    @Size(max = 50)
    @Column(name = "cargo", length = 50)
    private String cargo;

    @Size(max = 50)
    @Column(name = "telefono_contacto", length = 50)
    private String telefonoContacto;

    @Column(name = "crea_libro_admin_man")
    private Boolean creaLibroAdminMan;

    @Column(name = "crea_libro_admin_con")
    private Boolean creaLibroAdminCon;

    @Column(name = "actualizar_contrato_admin_man")
    private Boolean actualizarContratoAdminMan;

    @Column(name = "actualizar_contrato_admin_con")
    private Boolean actualizarContratoAdminCon;

    @Size(max = 50)
    @Column(name = "telefono_contacto_secundario", length = 50)
    private String telefonoContactoSecundario;

    @Size(max = 50)
    @Column(name = "email_contacto", length = 50)
    private String emailContacto;

    @Column(name = "id_dependencia_contratista")
    private Integer idDependenciaContratista;

    @OneToMany(mappedBy = "contrato")
    private Set<Libro> libros = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private Dependencia dependenciaMandante;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private TipoContrato tipoContrato;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private Modalidad modalidad;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private Comuna comuna;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private TipoMoneda tipoMoneda;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private TipoMonto tipoMonto;

    @ManyToOne
    @JsonIgnoreProperties("contratoes")
    private EstadoServicio estadoServicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaInicioServicio() {
        return fechaInicioServicio;
    }

    public Contrato fechaInicioServicio(Instant fechaInicioServicio) {
        this.fechaInicioServicio = fechaInicioServicio;
        return this;
    }

    public void setFechaInicioServicio(Instant fechaInicioServicio) {
        this.fechaInicioServicio = fechaInicioServicio;
    }

    public Instant getFechaTerminoServicio() {
        return fechaTerminoServicio;
    }

    public Contrato fechaTerminoServicio(Instant fechaTerminoServicio) {
        this.fechaTerminoServicio = fechaTerminoServicio;
        return this;
    }

    public void setFechaTerminoServicio(Instant fechaTerminoServicio) {
        this.fechaTerminoServicio = fechaTerminoServicio;
    }

    public Instant getFechaTerminoAcceso() {
        return fechaTerminoAcceso;
    }

    public Contrato fechaTerminoAcceso(Instant fechaTerminoAcceso) {
        this.fechaTerminoAcceso = fechaTerminoAcceso;
        return this;
    }

    public void setFechaTerminoAcceso(Instant fechaTerminoAcceso) {
        this.fechaTerminoAcceso = fechaTerminoAcceso;
    }

    public String getObservacionesServicio() {
        return observacionesServicio;
    }

    public Contrato observacionesServicio(String observacionesServicio) {
        this.observacionesServicio = observacionesServicio;
        return this;
    }

    public void setObservacionesServicio(String observacionesServicio) {
        this.observacionesServicio = observacionesServicio;
    }

    public String getCodigo() {
        return codigo;
    }

    public Contrato codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Contrato nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Contrato descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoOtro() {
        return tipoOtro;
    }

    public Contrato tipoOtro(String tipoOtro) {
        this.tipoOtro = tipoOtro;
        return this;
    }

    public void setTipoOtro(String tipoOtro) {
        this.tipoOtro = tipoOtro;
    }

    public String getModalidadOtra() {
        return modalidadOtra;
    }

    public Contrato modalidadOtra(String modalidadOtra) {
        this.modalidadOtra = modalidadOtra;
        return this;
    }

    public void setModalidadOtra(String modalidadOtra) {
        this.modalidadOtra = modalidadOtra;
    }

    public String getDireccion() {
        return direccion;
    }

    public Contrato direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getMonto() {
        return monto;
    }

    public Contrato monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public Contrato fechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaTermino() {
        return fechaTermino;
    }

    public Contrato fechaTermino(Instant fechaTermino) {
        this.fechaTermino = fechaTermino;
        return this;
    }

    public void setFechaTermino(Instant fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Contrato observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public Contrato nombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
        return this;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getCargo() {
        return cargo;
    }

    public Contrato cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public Contrato telefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Boolean isCreaLibroAdminMan() {
        return creaLibroAdminMan;
    }

    public Contrato creaLibroAdminMan(Boolean creaLibroAdminMan) {
        this.creaLibroAdminMan = creaLibroAdminMan;
        return this;
    }

    public void setCreaLibroAdminMan(Boolean creaLibroAdminMan) {
        this.creaLibroAdminMan = creaLibroAdminMan;
    }

    public Boolean isCreaLibroAdminCon() {
        return creaLibroAdminCon;
    }

    public Contrato creaLibroAdminCon(Boolean creaLibroAdminCon) {
        this.creaLibroAdminCon = creaLibroAdminCon;
        return this;
    }

    public void setCreaLibroAdminCon(Boolean creaLibroAdminCon) {
        this.creaLibroAdminCon = creaLibroAdminCon;
    }

    public Boolean isActualizarContratoAdminMan() {
        return actualizarContratoAdminMan;
    }

    public Contrato actualizarContratoAdminMan(Boolean actualizarContratoAdminMan) {
        this.actualizarContratoAdminMan = actualizarContratoAdminMan;
        return this;
    }

    public void setActualizarContratoAdminMan(Boolean actualizarContratoAdminMan) {
        this.actualizarContratoAdminMan = actualizarContratoAdminMan;
    }

    public Boolean isActualizarContratoAdminCon() {
        return actualizarContratoAdminCon;
    }

    public Contrato actualizarContratoAdminCon(Boolean actualizarContratoAdminCon) {
        this.actualizarContratoAdminCon = actualizarContratoAdminCon;
        return this;
    }

    public void setActualizarContratoAdminCon(Boolean actualizarContratoAdminCon) {
        this.actualizarContratoAdminCon = actualizarContratoAdminCon;
    }

    public String getTelefonoContactoSecundario() {
        return telefonoContactoSecundario;
    }

    public Contrato telefonoContactoSecundario(String telefonoContactoSecundario) {
        this.telefonoContactoSecundario = telefonoContactoSecundario;
        return this;
    }

    public void setTelefonoContactoSecundario(String telefonoContactoSecundario) {
        this.telefonoContactoSecundario = telefonoContactoSecundario;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public Contrato emailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
        return this;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public Integer getIdDependenciaContratista() {
        return idDependenciaContratista;
    }

    public Contrato idDependenciaContratista(Integer idDependenciaContratista) {
        this.idDependenciaContratista = idDependenciaContratista;
        return this;
    }

    public void setIdDependenciaContratista(Integer idDependenciaContratista) {
        this.idDependenciaContratista = idDependenciaContratista;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public Contrato libros(Set<Libro> libros) {
        this.libros = libros;
        return this;
    }

    public Contrato addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setContrato(this);
        return this;
    }

    public Contrato removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setContrato(null);
        return this;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    public Dependencia getDependenciaMandante() {
        return dependenciaMandante;
    }

    public Contrato dependenciaMandante(Dependencia dependencia) {
        this.dependenciaMandante = dependencia;
        return this;
    }

    public void setDependenciaMandante(Dependencia dependencia) {
        this.dependenciaMandante = dependencia;
    }

    public Region getRegion() {
        return region;
    }

    public Contrato region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public Contrato tipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
        return this;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public Contrato modalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public Contrato comuna(Comuna comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public Contrato tipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
        return this;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public TipoMonto getTipoMonto() {
        return tipoMonto;
    }

    public Contrato tipoMonto(TipoMonto tipoMonto) {
        this.tipoMonto = tipoMonto;
        return this;
    }

    public void setTipoMonto(TipoMonto tipoMonto) {
        this.tipoMonto = tipoMonto;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public Contrato estadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
        return this;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrato)) {
            return false;
        }
        return id != null && id.equals(((Contrato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contrato{" +
            "id=" + getId() +
            ", fechaInicioServicio='" + getFechaInicioServicio() + "'" +
            ", fechaTerminoServicio='" + getFechaTerminoServicio() + "'" +
            ", fechaTerminoAcceso='" + getFechaTerminoAcceso() + "'" +
            ", observacionesServicio='" + getObservacionesServicio() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoOtro='" + getTipoOtro() + "'" +
            ", modalidadOtra='" + getModalidadOtra() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", monto=" + getMonto() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaTermino='" + getFechaTermino() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", creaLibroAdminMan='" + isCreaLibroAdminMan() + "'" +
            ", creaLibroAdminCon='" + isCreaLibroAdminCon() + "'" +
            ", actualizarContratoAdminMan='" + isActualizarContratoAdminMan() + "'" +
            ", actualizarContratoAdminCon='" + isActualizarContratoAdminCon() + "'" +
            ", telefonoContactoSecundario='" + getTelefonoContactoSecundario() + "'" +
            ", emailContacto='" + getEmailContacto() + "'" +
            ", idDependenciaContratista=" + getIdDependenciaContratista() +
            "}";
    }
}
