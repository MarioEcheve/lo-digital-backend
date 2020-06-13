package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A UsuarioDependencia.
 */
@Entity
@Table(name = "usuario_dependencia")
public class UsuarioDependencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "usuarioDependencia")
    private Set<UsuarioLibro> usuarioLibros = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("usuarioDependencias")
    private Dependencia dependencia;

    @ManyToOne
    @JsonIgnoreProperties("usuarioDependencias")
    private Usuario usuario;

    @ManyToOne
    @JsonIgnoreProperties("usuarioDependencias")
    private PerfilUsuarioDependencia perfilUsuarioDependencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public UsuarioDependencia fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public UsuarioDependencia fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean isEstado() {
        return estado;
    }

    public UsuarioDependencia estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<UsuarioLibro> getUsuarioLibros() {
        return usuarioLibros;
    }

    public UsuarioDependencia usuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
        return this;
    }

    public UsuarioDependencia addUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.add(usuarioLibro);
        usuarioLibro.setUsuarioDependencia(this);
        return this;
    }

    public UsuarioDependencia removeUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.remove(usuarioLibro);
        usuarioLibro.setUsuarioDependencia(null);
        return this;
    }

    public void setUsuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public UsuarioDependencia dependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
        return this;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public UsuarioDependencia usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PerfilUsuarioDependencia getPerfilUsuarioDependencia() {
        return perfilUsuarioDependencia;
    }

    public UsuarioDependencia perfilUsuarioDependencia(PerfilUsuarioDependencia perfilUsuarioDependencia) {
        this.perfilUsuarioDependencia = perfilUsuarioDependencia;
        return this;
    }

    public void setPerfilUsuarioDependencia(PerfilUsuarioDependencia perfilUsuarioDependencia) {
        this.perfilUsuarioDependencia = perfilUsuarioDependencia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDependencia)) {
            return false;
        }
        return id != null && id.equals(((UsuarioDependencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsuarioDependencia{" +
            "id=" + getId() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
