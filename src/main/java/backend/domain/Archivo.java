package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Archivo.
 */
@Entity
@Table(name = "archivo")
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "archivo", nullable = false)
    private byte[] archivo;

    @Column(name = "archivo_content_type", nullable = false)
    private String archivoContentType;

    @Size(max = 30)
    @Column(name = "descripcion", length = 30)
    private String descripcion;

    @NotNull
    @Column(name = "size", nullable = false)
    private Double size;

    @ManyToOne
    @JsonIgnoreProperties("archivos")
    private Folio folio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public Archivo archivo(byte[] archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getArchivoContentType() {
        return archivoContentType;
    }

    public Archivo archivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
        return this;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Archivo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getSize() {
        return size;
    }

    public Archivo size(Double size) {
        this.size = size;
        return this;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Folio getFolio() {
        return folio;
    }

    public Archivo folio(Folio folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archivo)) {
            return false;
        }
        return id != null && id.equals(((Archivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Archivo{" +
            "id=" + getId() +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", size=" + getSize() +
            "}";
    }
}
