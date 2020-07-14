package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A UsuarioLibro.
 */
@Entity
@Table(name = "usuario_libro")
public class UsuarioLibro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "cargo_funcion")
    private String cargoFuncion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToMany(mappedBy = "usuarioLibro")
    private Set<GesAlerta> gesAlertas = new HashSet<>();

    @OneToMany(mappedBy = "usuarioLibro")
    private Set<GesNota> gesNotas = new HashSet<>();

    @OneToMany(mappedBy = "usuarioLibro")
    private Set<GesFavorito> gesFavoritos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "usuarioLibros", allowSetters = true )
    private Libro libro;

    @ManyToOne
    @JsonIgnoreProperties("usuarioLibros")
    private UsuarioDependencia usuarioDependencia;

    @ManyToOne
    @JsonIgnoreProperties("usuarioLibros")
    private UsuarioLibroPerfil perfilUsuarioLibro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public UsuarioLibro estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getCargoFuncion() {
        return cargoFuncion;
    }

    public UsuarioLibro cargoFuncion(String cargoFuncion) {
        this.cargoFuncion = cargoFuncion;
        return this;
    }

    public void setCargoFuncion(String cargoFuncion) {
        this.cargoFuncion = cargoFuncion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public UsuarioLibro fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public UsuarioLibro fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<GesAlerta> getGesAlertas() {
        return gesAlertas;
    }

    public UsuarioLibro gesAlertas(Set<GesAlerta> gesAlertas) {
        this.gesAlertas = gesAlertas;
        return this;
    }

    public UsuarioLibro addGesAlerta(GesAlerta gesAlerta) {
        this.gesAlertas.add(gesAlerta);
        gesAlerta.setUsuarioLibro(this);
        return this;
    }

    public UsuarioLibro removeGesAlerta(GesAlerta gesAlerta) {
        this.gesAlertas.remove(gesAlerta);
        gesAlerta.setUsuarioLibro(null);
        return this;
    }

    public void setGesAlertas(Set<GesAlerta> gesAlertas) {
        this.gesAlertas = gesAlertas;
    }

    public Set<GesNota> getGesNotas() {
        return gesNotas;
    }

    public UsuarioLibro gesNotas(Set<GesNota> gesNotas) {
        this.gesNotas = gesNotas;
        return this;
    }

    public UsuarioLibro addGesNota(GesNota gesNota) {
        this.gesNotas.add(gesNota);
        gesNota.setUsuarioLibro(this);
        return this;
    }

    public UsuarioLibro removeGesNota(GesNota gesNota) {
        this.gesNotas.remove(gesNota);
        gesNota.setUsuarioLibro(null);
        return this;
    }

    public void setGesNotas(Set<GesNota> gesNotas) {
        this.gesNotas = gesNotas;
    }

    public Set<GesFavorito> getGesFavoritos() {
        return gesFavoritos;
    }

    public UsuarioLibro gesFavoritos(Set<GesFavorito> gesFavoritos) {
        this.gesFavoritos = gesFavoritos;
        return this;
    }

    public UsuarioLibro addGesFavorito(GesFavorito gesFavorito) {
        this.gesFavoritos.add(gesFavorito);
        gesFavorito.setUsuarioLibro(this);
        return this;
    }

    public UsuarioLibro removeGesFavorito(GesFavorito gesFavorito) {
        this.gesFavoritos.remove(gesFavorito);
        gesFavorito.setUsuarioLibro(null);
        return this;
    }

    public void setGesFavoritos(Set<GesFavorito> gesFavoritos) {
        this.gesFavoritos = gesFavoritos;
    }

    public Libro getLibro() {
        return libro;
    }

    public UsuarioLibro libro(Libro libro) {
        this.libro = libro;
        return this;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public UsuarioDependencia getUsuarioDependencia() {
        return usuarioDependencia;
    }

    public UsuarioLibro usuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencia = usuarioDependencia;
        return this;
    }

    public void setUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencia = usuarioDependencia;
    }

    public UsuarioLibroPerfil getPerfilUsuarioLibro() {
        return perfilUsuarioLibro;
    }

    public UsuarioLibro perfilUsuarioLibro(UsuarioLibroPerfil usuarioLibroPerfil) {
        this.perfilUsuarioLibro = usuarioLibroPerfil;
        return this;
    }

    public void setPerfilUsuarioLibro(UsuarioLibroPerfil usuarioLibroPerfil) {
        this.perfilUsuarioLibro = usuarioLibroPerfil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioLibro)) {
            return false;
        }
        return id != null && id.equals(((UsuarioLibro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsuarioLibro{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            ", cargoFuncion='" + getCargoFuncion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
