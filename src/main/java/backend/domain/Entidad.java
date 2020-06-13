package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Entidad.
 */
@Entity
@Table(name = "entidad")
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "rut", length = 15, nullable = false)
    private String rut;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Size(max = 100)
    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToMany(mappedBy = "entidad")
    private Set<Dependencia> dependencias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("entidads")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("entidads")
    private TipoEntidad tipoEntidad;

    @ManyToOne
    @JsonIgnoreProperties("entidads")
    private ActividadRubro actividadRubro;

    @ManyToOne
    @JsonIgnoreProperties("entidads")
    private Comuna comuna;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public Entidad rut(String rut) {
        this.rut = rut;
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public Entidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Entidad direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Entidad fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Entidad fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<Dependencia> getDependencias() {
        return dependencias;
    }

    public Entidad dependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
        return this;
    }

    public Entidad addDependencia(Dependencia dependencia) {
        this.dependencias.add(dependencia);
        dependencia.setEntidad(this);
        return this;
    }

    public Entidad removeDependencia(Dependencia dependencia) {
        this.dependencias.remove(dependencia);
        dependencia.setEntidad(null);
        return this;
    }

    public void setDependencias(Set<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }

    public Region getRegion() {
        return region;
    }

    public Entidad region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public Entidad tipoEntidad(TipoEntidad tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
        return this;
    }

    public void setTipoEntidad(TipoEntidad tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public ActividadRubro getActividadRubro() {
        return actividadRubro;
    }

    public Entidad actividadRubro(ActividadRubro actividadRubro) {
        this.actividadRubro = actividadRubro;
        return this;
    }

    public void setActividadRubro(ActividadRubro actividadRubro) {
        this.actividadRubro = actividadRubro;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public Entidad comuna(Comuna comuna) {
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
        if (!(o instanceof Entidad)) {
            return false;
        }
        return id != null && id.equals(((Entidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Entidad{" +
            "id=" + getId() +
            ", rut='" + getRut() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
