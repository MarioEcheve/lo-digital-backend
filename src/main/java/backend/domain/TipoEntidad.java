package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoEntidad.
 */
@Entity
@Table(name = "tipo_entidad")
public class TipoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoEntidad")
    private Set<Entidad> entidads = new HashSet<>();

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

    public TipoEntidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Entidad> getEntidads() {
        return entidads;
    }

    public TipoEntidad entidads(Set<Entidad> entidads) {
        this.entidads = entidads;
        return this;
    }

    public TipoEntidad addEntidad(Entidad entidad) {
        this.entidads.add(entidad);
        entidad.setTipoEntidad(this);
        return this;
    }

    public TipoEntidad removeEntidad(Entidad entidad) {
        this.entidads.remove(entidad);
        entidad.setTipoEntidad(null);
        return this;
    }

    public void setEntidads(Set<Entidad> entidads) {
        this.entidads = entidads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoEntidad)) {
            return false;
        }
        return id != null && id.equals(((TipoEntidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoEntidad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
