package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "region")
    private Set<Entidad> entidads = new HashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Dependencia> dependencias = new HashSet<>();

    @OneToMany(mappedBy = "region")
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

    public Region nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Entidad> getEntidads() {
        return entidads;
    }

    public Region entidads(Set<Entidad> entidads) {
        this.entidads = entidads;
        return this;
    }

    public Region addEntidad(Entidad entidad) {
        this.entidads.add(entidad);
        entidad.setRegion(this);
        return this;
    }

    public Region removeEntidad(Entidad entidad) {
        this.entidads.remove(entidad);
        entidad.setRegion(null);
        return this;
    }

    public void setEntidads(Set<Entidad> entidads) {
        this.entidads = entidads;
    }

    public Set<Dependencia> getDependencias() {
        return dependencias;
    }

    public Region dependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
        return this;
    }

    public Region addDependencia(Dependencia dependencia) {
        this.dependencias.add(dependencia);
        dependencia.setRegion(this);
        return this;
    }

    public Region removeDependencia(Dependencia dependencia) {
        this.dependencias.remove(dependencia);
        dependencia.setRegion(null);
        return this;
    }

    public void setDependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Region contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Region addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setRegion(this);
        return this;
    }

    public Region removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setRegion(null);
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
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
