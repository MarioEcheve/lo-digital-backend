package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoServicio.
 */
@Entity
@Table(name = "estado_servicio")
public class EstadoServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "estadoServicio")
    private Set<Contrato> contratoes = new HashSet<>();

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

    public EstadoServicio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public EstadoServicio contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public EstadoServicio addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setEstadoServicio(this);
        return this;
    }

    public EstadoServicio removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setEstadoServicio(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoServicio)) {
            return false;
        }
        return id != null && id.equals(((EstadoServicio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoServicio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
