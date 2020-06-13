package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoLibro.
 */
@Entity
@Table(name = "estado_libro")
public class EstadoLibro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "estadoLibro")
    private Set<Libro> libros = new HashSet<>();

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

    public EstadoLibro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public EstadoLibro libros(Set<Libro> libros) {
        this.libros = libros;
        return this;
    }

    public EstadoLibro addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setEstadoLibro(this);
        return this;
    }

    public EstadoLibro removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setEstadoLibro(null);
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
        if (!(o instanceof EstadoLibro)) {
            return false;
        }
        return id != null && id.equals(((EstadoLibro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoLibro{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
