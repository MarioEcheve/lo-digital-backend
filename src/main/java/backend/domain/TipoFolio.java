package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoFolio.
 */
@Entity
@Table(name = "tipo_folio")
public class TipoFolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "nombre", length = 20)
    private String nombre;

    @NotNull
    @Column(name = "visible_maestro", nullable = false)
    private Boolean visibleMaestro;

    @NotNull
    @Column(name = "visible_auxliar", nullable = false)
    private Boolean visibleAuxliar;

    @NotNull
    @Column(name = "visible_mandante", nullable = false)
    private Boolean visibleMandante;

    @NotNull
    @Column(name = "visible_contratista", nullable = false)
    private Boolean visibleContratista;

    @OneToMany(mappedBy = "tipoFolio")
    private Set<Folio> folios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoFolio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isVisibleMaestro() {
        return visibleMaestro;
    }

    public TipoFolio visibleMaestro(Boolean visibleMaestro) {
        this.visibleMaestro = visibleMaestro;
        return this;
    }

    public void setVisibleMaestro(Boolean visibleMaestro) {
        this.visibleMaestro = visibleMaestro;
    }

    public Boolean isVisibleAuxliar() {
        return visibleAuxliar;
    }

    public TipoFolio visibleAuxliar(Boolean visibleAuxliar) {
        this.visibleAuxliar = visibleAuxliar;
        return this;
    }

    public void setVisibleAuxliar(Boolean visibleAuxliar) {
        this.visibleAuxliar = visibleAuxliar;
    }

    public Boolean isVisibleMandante() {
        return visibleMandante;
    }

    public TipoFolio visibleMandante(Boolean visibleMandante) {
        this.visibleMandante = visibleMandante;
        return this;
    }

    public void setVisibleMandante(Boolean visibleMandante) {
        this.visibleMandante = visibleMandante;
    }

    public Boolean isVisibleContratista() {
        return visibleContratista;
    }

    public TipoFolio visibleContratista(Boolean visibleContratista) {
        this.visibleContratista = visibleContratista;
        return this;
    }

    public void setVisibleContratista(Boolean visibleContratista) {
        this.visibleContratista = visibleContratista;
    }

    public Set<Folio> getFolios() {
        return folios;
    }

    public TipoFolio folios(Set<Folio> folios) {
        this.folios = folios;
        return this;
    }

    public TipoFolio addFolio(Folio folio) {
        this.folios.add(folio);
        folio.setTipoFolio(this);
        return this;
    }

    public TipoFolio removeFolio(Folio folio) {
        this.folios.remove(folio);
        folio.setTipoFolio(null);
        return this;
    }

    public void setFolios(Set<Folio> folios) {
        this.folios = folios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoFolio)) {
            return false;
        }
        return id != null && id.equals(((TipoFolio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoFolio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", visibleMaestro='" + isVisibleMaestro() + "'" +
            ", visibleAuxliar='" + isVisibleAuxliar() + "'" +
            ", visibleMandante='" + isVisibleMandante() + "'" +
            ", visibleContratista='" + isVisibleContratista() + "'" +
            "}";
    }
}
