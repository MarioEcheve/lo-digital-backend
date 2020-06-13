package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoContrato.
 */
@Entity
@Table(name = "tipo_contrato")
public class TipoContrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoContrato")
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

    public TipoContrato nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public TipoContrato contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public TipoContrato addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setTipoContrato(this);
        return this;
    }

    public TipoContrato removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setTipoContrato(null);
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
        if (!(o instanceof TipoContrato)) {
            return false;
        }
        return id != null && id.equals(((TipoContrato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoContrato{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
