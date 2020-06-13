package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Libro.
 */
@Entity
@Table(name = "libro")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "codigo", length = 20)
    private String codigo;

    @Size(max = 60)
    @Column(name = "nombre", length = 60)
    private String nombre;

    @Size(max = 250)
    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_apertura")
    private Instant fechaApertura;

    @Column(name = "fecha_cierre")
    private Instant fechaCierre;

    @Column(name = "apertura_mandante")
    private Boolean aperturaMandante;

    @Column(name = "apertura_contratista")
    private Boolean aperturaContratista;

    @Column(name = "escritura_mandante")
    private Boolean escrituraMandante;

    @Column(name = "escritura_contratista")
    private Boolean escrituraContratista;

    @Column(name = "cierre_mandante")
    private Boolean cierreMandante;

    @Column(name = "cierre_contratista")
    private Boolean cierreContratista;

    @Column(name = "lectura_mandante")
    private Boolean lecturaMandante;

    @Column(name = "lectura_contratista")
    private Boolean lecturaContratista;

    @OneToMany(mappedBy = "libro")
    private Set<Folio> folios = new HashSet<>();

    @OneToMany(mappedBy = "libro")
    private Set<UsuarioLibro> usuarioLibros = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("libros")
    private Contrato contrato;

    @ManyToOne
    @JsonIgnoreProperties("libros")
    private TipoLibro tipoLibro;

    @ManyToOne
    @JsonIgnoreProperties("libros")
    private TipoFirma tipoFirma;

    @ManyToOne
    @JsonIgnoreProperties("libros")
    private EstadoLibro estadoLibro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Libro codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Libro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Libro descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Libro fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaApertura() {
        return fechaApertura;
    }

    public Libro fechaApertura(Instant fechaApertura) {
        this.fechaApertura = fechaApertura;
        return this;
    }

    public void setFechaApertura(Instant fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Instant getFechaCierre() {
        return fechaCierre;
    }

    public Libro fechaCierre(Instant fechaCierre) {
        this.fechaCierre = fechaCierre;
        return this;
    }

    public void setFechaCierre(Instant fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Boolean isAperturaMandante() {
        return aperturaMandante;
    }

    public Libro aperturaMandante(Boolean aperturaMandante) {
        this.aperturaMandante = aperturaMandante;
        return this;
    }

    public void setAperturaMandante(Boolean aperturaMandante) {
        this.aperturaMandante = aperturaMandante;
    }

    public Boolean isAperturaContratista() {
        return aperturaContratista;
    }

    public Libro aperturaContratista(Boolean aperturaContratista) {
        this.aperturaContratista = aperturaContratista;
        return this;
    }

    public void setAperturaContratista(Boolean aperturaContratista) {
        this.aperturaContratista = aperturaContratista;
    }

    public Boolean isEscrituraMandante() {
        return escrituraMandante;
    }

    public Libro escrituraMandante(Boolean escrituraMandante) {
        this.escrituraMandante = escrituraMandante;
        return this;
    }

    public void setEscrituraMandante(Boolean escrituraMandante) {
        this.escrituraMandante = escrituraMandante;
    }

    public Boolean isEscrituraContratista() {
        return escrituraContratista;
    }

    public Libro escrituraContratista(Boolean escrituraContratista) {
        this.escrituraContratista = escrituraContratista;
        return this;
    }

    public void setEscrituraContratista(Boolean escrituraContratista) {
        this.escrituraContratista = escrituraContratista;
    }

    public Boolean isCierreMandante() {
        return cierreMandante;
    }

    public Libro cierreMandante(Boolean cierreMandante) {
        this.cierreMandante = cierreMandante;
        return this;
    }

    public void setCierreMandante(Boolean cierreMandante) {
        this.cierreMandante = cierreMandante;
    }

    public Boolean isCierreContratista() {
        return cierreContratista;
    }

    public Libro cierreContratista(Boolean cierreContratista) {
        this.cierreContratista = cierreContratista;
        return this;
    }

    public void setCierreContratista(Boolean cierreContratista) {
        this.cierreContratista = cierreContratista;
    }

    public Boolean isLecturaMandante() {
        return lecturaMandante;
    }

    public Libro lecturaMandante(Boolean lecturaMandante) {
        this.lecturaMandante = lecturaMandante;
        return this;
    }

    public void setLecturaMandante(Boolean lecturaMandante) {
        this.lecturaMandante = lecturaMandante;
    }

    public Boolean isLecturaContratista() {
        return lecturaContratista;
    }

    public Libro lecturaContratista(Boolean lecturaContratista) {
        this.lecturaContratista = lecturaContratista;
        return this;
    }

    public void setLecturaContratista(Boolean lecturaContratista) {
        this.lecturaContratista = lecturaContratista;
    }

    public Set<Folio> getFolios() {
        return folios;
    }

    public Libro folios(Set<Folio> folios) {
        this.folios = folios;
        return this;
    }

    public Libro addFolio(Folio folio) {
        this.folios.add(folio);
        folio.setLibro(this);
        return this;
    }

    public Libro removeFolio(Folio folio) {
        this.folios.remove(folio);
        folio.setLibro(null);
        return this;
    }

    public void setFolios(Set<Folio> folios) {
        this.folios = folios;
    }

    public Set<UsuarioLibro> getUsuarioLibros() {
        return usuarioLibros;
    }

    public Libro usuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
        return this;
    }

    public Libro addUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.add(usuarioLibro);
        usuarioLibro.setLibro(this);
        return this;
    }

    public Libro removeUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.remove(usuarioLibro);
        usuarioLibro.setLibro(null);
        return this;
    }

    public void setUsuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public Libro contrato(Contrato contrato) {
        this.contrato = contrato;
        return this;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public TipoLibro getTipoLibro() {
        return tipoLibro;
    }

    public Libro tipoLibro(TipoLibro tipoLibro) {
        this.tipoLibro = tipoLibro;
        return this;
    }

    public void setTipoLibro(TipoLibro tipoLibro) {
        this.tipoLibro = tipoLibro;
    }

    public TipoFirma getTipoFirma() {
        return tipoFirma;
    }

    public Libro tipoFirma(TipoFirma tipoFirma) {
        this.tipoFirma = tipoFirma;
        return this;
    }

    public void setTipoFirma(TipoFirma tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public EstadoLibro getEstadoLibro() {
        return estadoLibro;
    }

    public Libro estadoLibro(EstadoLibro estadoLibro) {
        this.estadoLibro = estadoLibro;
        return this;
    }

    public void setEstadoLibro(EstadoLibro estadoLibro) {
        this.estadoLibro = estadoLibro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Libro)) {
            return false;
        }
        return id != null && id.equals(((Libro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Libro{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaApertura='" + getFechaApertura() + "'" +
            ", fechaCierre='" + getFechaCierre() + "'" +
            ", aperturaMandante='" + isAperturaMandante() + "'" +
            ", aperturaContratista='" + isAperturaContratista() + "'" +
            ", escrituraMandante='" + isEscrituraMandante() + "'" +
            ", escrituraContratista='" + isEscrituraContratista() + "'" +
            ", cierreMandante='" + isCierreMandante() + "'" +
            ", cierreContratista='" + isCierreContratista() + "'" +
            ", lecturaMandante='" + isLecturaMandante() + "'" +
            ", lecturaContratista='" + isLecturaContratista() + "'" +
            "}";
    }
}
