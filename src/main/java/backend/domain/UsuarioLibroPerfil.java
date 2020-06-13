package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UsuarioLibroPerfil.
 */
@Entity
@Table(name = "usuario_libro_perfil")
public class UsuarioLibroPerfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "perfilUsuarioLibro")
    private Set<UsuarioLibro> usuarioLibros = new HashSet<>();

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

    public UsuarioLibroPerfil nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<UsuarioLibro> getUsuarioLibros() {
        return usuarioLibros;
    }

    public UsuarioLibroPerfil usuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
        return this;
    }

    public UsuarioLibroPerfil addUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.add(usuarioLibro);
        usuarioLibro.setPerfilUsuarioLibro(this);
        return this;
    }

    public UsuarioLibroPerfil removeUsuarioLibro(UsuarioLibro usuarioLibro) {
        this.usuarioLibros.remove(usuarioLibro);
        usuarioLibro.setPerfilUsuarioLibro(null);
        return this;
    }

    public void setUsuarioLibros(Set<UsuarioLibro> usuarioLibros) {
        this.usuarioLibros = usuarioLibros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioLibroPerfil)) {
            return false;
        }
        return id != null && id.equals(((UsuarioLibroPerfil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsuarioLibroPerfil{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
