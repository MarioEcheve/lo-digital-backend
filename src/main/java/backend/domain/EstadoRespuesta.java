package backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoRespuesta.
 */
@Entity
@Table(name = "estado_respuesta")
public class EstadoRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "estadoRespuesta")
    private Set<Folio> folios = new HashSet<>();

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

    public EstadoRespuesta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Folio> getFolios() {
        return folios;
    }

    public EstadoRespuesta folios(Set<Folio> folios) {
        this.folios = folios;
        return this;
    }

    public EstadoRespuesta addFolio(Folio folio) {
        this.folios.add(folio);
        folio.setEstadoRespuesta(this);
        return this;
    }

    public EstadoRespuesta removeFolio(Folio folio) {
        this.folios.remove(folio);
        folio.setEstadoRespuesta(null);
        return this;
    }

    public void setFolios(Set<Folio> folios) {
        this.folios = folios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoRespuesta)) {
            return false;
        }
        return id != null && id.equals(((EstadoRespuesta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoRespuesta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
