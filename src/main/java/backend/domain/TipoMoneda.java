package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoMoneda.
 */
@Entity
@Table(name = "tipo_moneda")
public class TipoMoneda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipoMoneda")
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

    public TipoMoneda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public TipoMoneda contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public TipoMoneda addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setTipoMoneda(this);
        return this;
    }

    public TipoMoneda removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setTipoMoneda(null);
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
        if (!(o instanceof TipoMoneda)) {
            return false;
        }
        return id != null && id.equals(((TipoMoneda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoMoneda{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
