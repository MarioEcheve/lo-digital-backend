package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoMonto.
 */
@Entity
@Table(name = "tipo_monto")
public class TipoMonto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipoMonto")
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

    public TipoMonto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public TipoMonto contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public TipoMonto addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setTipoMonto(this);
        return this;
    }

    public TipoMonto removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setTipoMonto(null);
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
        if (!(o instanceof TipoMonto)) {
            return false;
        }
        return id != null && id.equals(((TipoMonto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoMonto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
