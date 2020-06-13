package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ActividadRubro.
 */
@Entity
@Table(name = "actividad_rubro")
public class ActividadRubro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "actividadRubro")
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

    public ActividadRubro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Entidad> getEntidads() {
        return entidads;
    }

    public ActividadRubro entidads(Set<Entidad> entidads) {
        this.entidads = entidads;
        return this;
    }

    public ActividadRubro addEntidad(Entidad entidad) {
        this.entidads.add(entidad);
        entidad.setActividadRubro(this);
        return this;
    }

    public ActividadRubro removeEntidad(Entidad entidad) {
        this.entidads.remove(entidad);
        entidad.setActividadRubro(null);
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
        if (!(o instanceof ActividadRubro)) {
            return false;
        }
        return id != null && id.equals(((ActividadRubro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActividadRubro{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
