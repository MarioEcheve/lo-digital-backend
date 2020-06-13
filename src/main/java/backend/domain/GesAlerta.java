package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A GesAlerta.
 */
@Entity
@Table(name = "ges_alerta")
public class GesAlerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nota", length = 100, nullable = false)
    private String nota;

    @NotNull
    @Column(name = "fecha_alerta", nullable = false)
    private Instant fechaAlerta;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @ManyToOne
    @JsonIgnoreProperties("gesAlertas")
    private Folio folio;

    @ManyToOne
    @JsonIgnoreProperties("gesAlertas")
    private UsuarioLibro usuarioLibro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public GesAlerta nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Instant getFechaAlerta() {
        return fechaAlerta;
    }

    public GesAlerta fechaAlerta(Instant fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
        return this;
    }

    public void setFechaAlerta(Instant fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public GesAlerta fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public GesAlerta fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Folio getFolio() {
        return folio;
    }

    public GesAlerta folio(Folio folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    public UsuarioLibro getUsuarioLibro() {
        return usuarioLibro;
    }

    public GesAlerta usuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibro = usuarioLibro;
        return this;
    }

    public void setUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibro = usuarioLibro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GesAlerta)) {
            return false;
        }
        return id != null && id.equals(((GesAlerta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GesAlerta{" +
            "id=" + getId() +
            ", nota='" + getNota() + "'" +
            ", fechaAlerta='" + getFechaAlerta() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
