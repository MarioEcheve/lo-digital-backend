package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoLibro.
 */
@Entity
@Table(name = "tipo_libro")
public class TipoLibro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoLibro")
    private Set<Libro> libros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoLibro descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public TipoLibro libros(Set<Libro> libros) {
        this.libros = libros;
        return this;
    }

    public TipoLibro addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setTipoLibro(this);
        return this;
    }

    public TipoLibro removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setTipoLibro(null);
        return this;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoLibro)) {
            return false;
        }
        return id != null && id.equals(((TipoLibro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoLibro{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
