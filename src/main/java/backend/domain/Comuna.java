package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Comuna.
 */
@Entity
@Table(name = "comuna")
public class Comuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "comuna")
    private Set<Contrato> contratoes = new HashSet<>();

    @OneToMany(mappedBy = "comuna")
    private Set<Entidad> entidads = new HashSet<>();

    @OneToMany(mappedBy = "comuna")
    private Set<Dependencia> dependencias = new HashSet<>();

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

    public Comuna nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Comuna contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Comuna addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setComuna(this);
        return this;
    }

    public Comuna removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setComuna(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
    }

    public Set<Entidad> getEntidads() {
        return entidads;
    }

    public Comuna entidads(Set<Entidad> entidads) {
        this.entidads = entidads;
        return this;
    }

    public Comuna addEntidad(Entidad entidad) {
        this.entidads.add(entidad);
        entidad.setComuna(this);
        return this;
    }

    public Comuna removeEntidad(Entidad entidad) {
        this.entidads.remove(entidad);
        entidad.setComuna(null);
        return this;
    }

    public void setEntidads(Set<Entidad> entidads) {
        this.entidads = entidads;
    }

    public Set<Dependencia> getDependencias() {
        return dependencias;
    }

    public Comuna dependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
        return this;
    }

    public Comuna addDependencia(Dependencia dependencia) {
        this.dependencias.add(dependencia);
        dependencia.setComuna(this);
        return this;
    }

    public Comuna removeDependencia(Dependencia dependencia) {
        this.dependencias.remove(dependencia);
        dependencia.setComuna(null);
        return this;
    }

    public void setDependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comuna)) {
            return false;
        }
        return id != null && id.equals(((Comuna) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comuna{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
