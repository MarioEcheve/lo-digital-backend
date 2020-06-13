package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PerfilUsuarioDependencia.
 */
@Entity
@Table(name = "perfil_usuario_dependencia")
public class PerfilUsuarioDependencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "perfilUsuarioDependencia")
    private Set<UsuarioDependencia> usuarioDependencias = new HashSet<>();

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

    public PerfilUsuarioDependencia nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<UsuarioDependencia> getUsuarioDependencias() {
        return usuarioDependencias;
    }

    public PerfilUsuarioDependencia usuarioDependencias(Set<UsuarioDependencia> usuarioDependencias) {
        this.usuarioDependencias = usuarioDependencias;
        return this;
    }

    public PerfilUsuarioDependencia addUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.add(usuarioDependencia);
        usuarioDependencia.setPerfilUsuarioDependencia(this);
        return this;
    }

    public PerfilUsuarioDependencia removeUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.remove(usuarioDependencia);
        usuarioDependencia.setPerfilUsuarioDependencia(null);
        return this;
    }

    public void setUsuarioDependencias(Set<UsuarioDependencia> usuarioDependencias) {
        this.usuarioDependencias = usuarioDependencias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilUsuarioDependencia)) {
            return false;
        }
        return id != null && id.equals(((PerfilUsuarioDependencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PerfilUsuarioDependencia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
