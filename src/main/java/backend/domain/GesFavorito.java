package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A GesFavorito.
 */
@Entity
@Table(name = "ges_favorito")
public class GesFavorito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nota", length = 100, nullable = false)
    private String nota;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @Column(name = "favorito")
    private Boolean favorito;

    @ManyToOne
    @JsonIgnoreProperties(value="gesFavoritos", allowSetters=true)
    private Folio folio;

    @ManyToOne
    @JsonIgnoreProperties(value="gesFavoritos", allowSetters=true)
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

    public GesFavorito nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public GesFavorito fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public GesFavorito fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean isFavorito() {
        return favorito;
    }

    public GesFavorito favorito(Boolean favorito) {
        this.favorito = favorito;
        return this;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Folio getFolio() {
        return folio;
    }

    public GesFavorito folio(Folio folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    public UsuarioLibro getUsuarioLibro() {
        return usuarioLibro;
    }

    public GesFavorito usuarioLibro(UsuarioLibro usuarioLibro) {
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
        if (!(o instanceof GesFavorito)) {
            return false;
        }
        return id != null && id.equals(((GesFavorito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GesFavorito{" +
            "id=" + getId() +
            ", nota='" + getNota() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", favorito='" + isFavorito() + "'" +
            "}";
    }
}
