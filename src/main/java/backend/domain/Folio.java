package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Folio.
 */
@Entity
@Table(name = "folio")
public class Folio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_usuario_creador")
    private Integer idUsuarioCreador;

    @Column(name = "id_usuario_firma")
    private Integer idUsuarioFirma;

    @Column(name = "id_usuario_lectura")
    private Integer idUsuarioLectura;

    @Column(name = "numero_folio")
    private Integer numeroFolio;

    @Column(name = "requiere_respuesta")
    private Boolean requiereRespuesta;

    @Column(name = "fecha_requerida")
    private Instant fechaRequerida;

    @Column(name = "estado_lectura")
    private Boolean estadoLectura;

    @Column(name = "estado_folio")
    private Boolean estadoFolio;

    @Column(name = "entidad_creacion")
    private Boolean entidadCreacion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @Column(name = "fecha_firma")
    private Instant fechaFirma;

    @Column(name = "fecha_lectura")
    private Instant fechaLectura;

    @NotNull
    @Size(max = 80)
    @Column(name = "asunto", length = 80, nullable = false)
    private String asunto;

    @Size(max = 50000)
    @Column(name = "anotacion", length = 50000)
    private String anotacion;

    @Lob
    @Column(name = "pdf_firmado")
    private byte[] pdfFirmado;

    @Column(name = "pdf_firmado_content_type")
    private String pdfFirmadoContentType;

    @Lob
    @Column(name = "pdf_lectura")
    private byte[] pdfLectura;

    @Column(name = "pdf_lectura_content_type")
    private String pdfLecturaContentType;

    @OneToMany(mappedBy = "folio")
    private Set<Archivo> archivos = new HashSet<>();

    @OneToMany(mappedBy = "folio")
    private Set<GesAlerta> gesAlertas = new HashSet<>();

    @OneToMany(mappedBy = "folio")
    private Set<GesNota> gesNotas = new HashSet<>();

    @OneToMany(mappedBy = "folio")
    private Set<GesFavorito> gesFavoritos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value="folios",allowSetters=true)
    private Libro libro;

    @ManyToOne
    @JsonIgnoreProperties(value="folios",allowSetters=true)
    private TipoFolio tipoFolio;

    @ManyToOne
    @JsonIgnoreProperties(value="folios",allowSetters=true)
    private EstadoRespuesta estadoRespuesta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public Folio idUsuarioCreador(Integer idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
        return this;
    }

    public void setIdUsuarioCreador(Integer idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Integer getIdUsuarioFirma() {
        return idUsuarioFirma;
    }

    public Folio idUsuarioFirma(Integer idUsuarioFirma) {
        this.idUsuarioFirma = idUsuarioFirma;
        return this;
    }

    public void setIdUsuarioFirma(Integer idUsuarioFirma) {
        this.idUsuarioFirma = idUsuarioFirma;
    }

    public Integer getIdUsuarioLectura() {
        return idUsuarioLectura;
    }

    public Folio idUsuarioLectura(Integer idUsuarioLectura) {
        this.idUsuarioLectura = idUsuarioLectura;
        return this;
    }

    public void setIdUsuarioLectura(Integer idUsuarioLectura) {
        this.idUsuarioLectura = idUsuarioLectura;
    }

    public Integer getNumeroFolio() {
        return numeroFolio;
    }

    public Folio numeroFolio(Integer numeroFolio) {
        this.numeroFolio = numeroFolio;
        return this;
    }

    public void setNumeroFolio(Integer numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public Boolean isRequiereRespuesta() {
        return requiereRespuesta;
    }

    public Folio requiereRespuesta(Boolean requiereRespuesta) {
        this.requiereRespuesta = requiereRespuesta;
        return this;
    }

    public void setRequiereRespuesta(Boolean requiereRespuesta) {
        this.requiereRespuesta = requiereRespuesta;
    }

    public Instant getFechaRequerida() {
        return fechaRequerida;
    }

    public Folio fechaRequerida(Instant fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
        return this;
    }

    public void setFechaRequerida(Instant fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public Boolean isEstadoLectura() {
        return estadoLectura;
    }

    public Folio estadoLectura(Boolean estadoLectura) {
        this.estadoLectura = estadoLectura;
        return this;
    }

    public void setEstadoLectura(Boolean estadoLectura) {
        this.estadoLectura = estadoLectura;
    }

    public Boolean isEstadoFolio() {
        return estadoFolio;
    }

    public Folio estadoFolio(Boolean estadoFolio) {
        this.estadoFolio = estadoFolio;
        return this;
    }

    public void setEstadoFolio(Boolean estadoFolio) {
        this.estadoFolio = estadoFolio;
    }

    public Boolean isEntidadCreacion() {
        return entidadCreacion;
    }

    public Folio entidadCreacion(Boolean entidadCreacion) {
        this.entidadCreacion = entidadCreacion;
        return this;
    }

    public void setEntidadCreacion(Boolean entidadCreacion) {
        this.entidadCreacion = entidadCreacion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Folio fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Folio fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Instant getFechaFirma() {
        return fechaFirma;
    }

    public Folio fechaFirma(Instant fechaFirma) {
        this.fechaFirma = fechaFirma;
        return this;
    }

    public void setFechaFirma(Instant fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public Instant getFechaLectura() {
        return fechaLectura;
    }

    public Folio fechaLectura(Instant fechaLectura) {
        this.fechaLectura = fechaLectura;
        return this;
    }

    public void setFechaLectura(Instant fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public String getAsunto() {
        return asunto;
    }

    public Folio asunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public Folio anotacion(String anotacion) {
        this.anotacion = anotacion;
        return this;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public byte[] getPdfFirmado() {
        return pdfFirmado;
    }

    public Folio pdfFirmado(byte[] pdfFirmado) {
        this.pdfFirmado = pdfFirmado;
        return this;
    }

    public void setPdfFirmado(byte[] pdfFirmado) {
        this.pdfFirmado = pdfFirmado;
    }

    public String getPdfFirmadoContentType() {
        return pdfFirmadoContentType;
    }

    public Folio pdfFirmadoContentType(String pdfFirmadoContentType) {
        this.pdfFirmadoContentType = pdfFirmadoContentType;
        return this;
    }

    public void setPdfFirmadoContentType(String pdfFirmadoContentType) {
        this.pdfFirmadoContentType = pdfFirmadoContentType;
    }

    public byte[] getPdfLectura() {
        return pdfLectura;
    }

    public Folio pdfLectura(byte[] pdfLectura) {
        this.pdfLectura = pdfLectura;
        return this;
    }

    public void setPdfLectura(byte[] pdfLectura) {
        this.pdfLectura = pdfLectura;
    }

    public String getPdfLecturaContentType() {
        return pdfLecturaContentType;
    }

    public Folio pdfLecturaContentType(String pdfLecturaContentType) {
        this.pdfLecturaContentType = pdfLecturaContentType;
        return this;
    }

    public void setPdfLecturaContentType(String pdfLecturaContentType) {
        this.pdfLecturaContentType = pdfLecturaContentType;
    }

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public Folio archivos(Set<Archivo> archivos) {
        this.archivos = archivos;
        return this;
    }

    public Folio addArchivo(Archivo archivo) {
        this.archivos.add(archivo);
        archivo.setFolio(this);
        return this;
    }

    public Folio removeArchivo(Archivo archivo) {
        this.archivos.remove(archivo);
        archivo.setFolio(null);
        return this;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Set<GesAlerta> getGesAlertas() {
        return gesAlertas;
    }

    public Folio gesAlertas(Set<GesAlerta> gesAlertas) {
        this.gesAlertas = gesAlertas;
        return this;
    }

    public Folio addGesAlerta(GesAlerta gesAlerta) {
        this.gesAlertas.add(gesAlerta);
        gesAlerta.setFolio(this);
        return this;
    }

    public Folio removeGesAlerta(GesAlerta gesAlerta) {
        this.gesAlertas.remove(gesAlerta);
        gesAlerta.setFolio(null);
        return this;
    }

    public void setGesAlertas(Set<GesAlerta> gesAlertas) {
        this.gesAlertas = gesAlertas;
    }

    public Set<GesNota> getGesNotas() {
        return gesNotas;
    }

    public Folio gesNotas(Set<GesNota> gesNotas) {
        this.gesNotas = gesNotas;
        return this;
    }

    public Folio addGesNota(GesNota gesNota) {
        this.gesNotas.add(gesNota);
        gesNota.setFolio(this);
        return this;
    }

    public Folio removeGesNota(GesNota gesNota) {
        this.gesNotas.remove(gesNota);
        gesNota.setFolio(null);
        return this;
    }

    public void setGesNotas(Set<GesNota> gesNotas) {
        this.gesNotas = gesNotas;
    }

    public Set<GesFavorito> getGesFavoritos() {
        return gesFavoritos;
    }

    public Folio gesFavoritos(Set<GesFavorito> gesFavoritos) {
        this.gesFavoritos = gesFavoritos;
        return this;
    }

    public Folio addGesFavorito(GesFavorito gesFavorito) {
        this.gesFavoritos.add(gesFavorito);
        gesFavorito.setFolio(this);
        return this;
    }

    public Folio removeGesFavorito(GesFavorito gesFavorito) {
        this.gesFavoritos.remove(gesFavorito);
        gesFavorito.setFolio(null);
        return this;
    }

    public void setGesFavoritos(Set<GesFavorito> gesFavoritos) {
        this.gesFavoritos = gesFavoritos;
    }

    public Libro getLibro() {
        return libro;
    }

    public Folio libro(Libro libro) {
        this.libro = libro;
        return this;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public TipoFolio getTipoFolio() {
        return tipoFolio;
    }

    public Folio tipoFolio(TipoFolio tipoFolio) {
        this.tipoFolio = tipoFolio;
        return this;
    }

    public void setTipoFolio(TipoFolio tipoFolio) {
        this.tipoFolio = tipoFolio;
    }

    public EstadoRespuesta getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public Folio estadoRespuesta(EstadoRespuesta estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
        return this;
    }

    public void setEstadoRespuesta(EstadoRespuesta estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Folio)) {
            return false;
        }
        return id != null && id.equals(((Folio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Folio{" +
            "id=" + getId() +
            ", idUsuarioCreador=" + getIdUsuarioCreador() +
            ", idUsuarioFirma=" + getIdUsuarioFirma() +
            ", idUsuarioLectura=" + getIdUsuarioLectura() +
            ", numeroFolio=" + getNumeroFolio() +
            ", requiereRespuesta='" + isRequiereRespuesta() + "'" +
            ", fechaRequerida='" + getFechaRequerida() + "'" +
            ", estadoLectura='" + isEstadoLectura() + "'" +
            ", estadoFolio='" + isEstadoFolio() + "'" +
            ", entidadCreacion='" + isEntidadCreacion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", fechaFirma='" + getFechaFirma() + "'" +
            ", fechaLectura='" + getFechaLectura() + "'" +
            ", asunto='" + getAsunto() + "'" +
            ", anotacion='" + getAnotacion() + "'" +
            ", pdfFirmado='" + getPdfFirmado() + "'" +
            ", pdfFirmadoContentType='" + getPdfFirmadoContentType() + "'" +
            ", pdfLectura='" + getPdfLectura() + "'" +
            ", pdfLecturaContentType='" + getPdfLecturaContentType() + "'" +
            "}";
    }
}