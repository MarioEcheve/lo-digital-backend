package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dependencia.
 */
@Entity
@Table(name = "dependencia")
public class Dependencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Size(max = 110)
    @Column(name = "direccion", length = 110)
    private String direccion;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToMany(mappedBy = "dependencia")
    private Set<UsuarioDependencia> usuarioDependencias = new HashSet<>();

    @OneToMany(mappedBy = "dependenciaMandante")
    private Set<Contrato> contratoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value ="dependencias" , allowSetters = true)
    private Entidad entidad;

    @ManyToOne
    @JsonIgnoreProperties("dependencias")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("dependencias")
    private Comuna comuna;

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

    public Dependencia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Dependencia direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Dependencia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Dependencia fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Dependencia fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<UsuarioDependencia> getUsuarioDependencias() {
        return usuarioDependencias;
    }

    public Dependencia usuarioDependencias(Set<UsuarioDependencia> usuarioDependencias) {
        this.usuarioDependencias = usuarioDependencias;
        return this;
    }

    public Dependencia addUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.add(usuarioDependencia);
        usuarioDependencia.setDependencia(this);
        return this;
    }

    public Dependencia removeUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.remove(usuarioDependencia);
        usuarioDependencia.setDependencia(null);
        return this;
    }

    public void setUsuarioDependencias(Set<UsuarioDependencia> usuarioDependencias) {
        this.usuarioDependencias = usuarioDependencias;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Dependencia contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Dependencia addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setDependenciaMandante(this);
        return this;
    }

    public Dependencia removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setDependenciaMandante(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public Dependencia entidad(Entidad entidad) {
        this.entidad = entidad;
        return this;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Region getRegion() {
        return region;
    }

    public Dependencia region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public Dependencia comuna(Comuna comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dependencia)) {
            return false;
        }
        return id != null && id.equals(((Dependencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dependencia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
