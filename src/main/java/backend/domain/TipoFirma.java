package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoFirma.
 */
@Entity
@Table(name = "tipo_firma")
public class TipoFirma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipoFirma")
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

    public TipoFirma nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public TipoFirma libros(Set<Libro> libros) {
        this.libros = libros;
        return this;
    }

    public TipoFirma addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setTipoFirma(this);
        return this;
    }

    public TipoFirma removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setTipoFirma(null);
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
        if (!(o instanceof TipoFirma)) {
            return false;
        }
        return id != null && id.equals(((TipoFirma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoFirma{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
