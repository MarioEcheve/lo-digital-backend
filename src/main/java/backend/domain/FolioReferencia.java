package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FolioReferencia.
 */
@Entity
@Table(name = "folio_referencia")
public class FolioReferencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "id_folio_origen")
    private Integer idFolioOrigen;

    @Column(name = "id_folio_referencia")
    private Integer idFolioReferencia;

    @ManyToMany(mappedBy = "folioReferencias")
    @JsonIgnore
    private Set<Folio> folios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public FolioReferencia asunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getIdFolioOrigen() {
        return idFolioOrigen;
    }

    public FolioReferencia idFolioOrigen(Integer idFolioOrigen) {
        this.idFolioOrigen = idFolioOrigen;
        return this;
    }

    public void setIdFolioOrigen(Integer idFolioOrigen) {
        this.idFolioOrigen = idFolioOrigen;
    }

    public Integer getIdFolioReferencia() {
        return idFolioReferencia;
    }

    public FolioReferencia idFolioReferencia(Integer idFolioReferencia) {
        this.idFolioReferencia = idFolioReferencia;
        return this;
    }

    public void setIdFolioReferencia(Integer idFolioReferencia) {
        this.idFolioReferencia = idFolioReferencia;
    }

    public Set<Folio> getFolios() {
        return folios;
    }

    public FolioReferencia folios(Set<Folio> folios) {
        this.folios = folios;
        return this;
    }

    public FolioReferencia addFolio(Folio folio) {
        this.folios.add(folio);
        folio.getFolioReferencias().add(this);
        return this;
    }

    public FolioReferencia removeFolio(Folio folio) {
        this.folios.remove(folio);
        folio.getFolioReferencias().remove(this);
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
        if (!(o instanceof FolioReferencia)) {
            return false;
        }
        return id != null && id.equals(((FolioReferencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FolioReferencia{" +
            "id=" + getId() +
            ", asunto='" + getAsunto() + "'" +
            ", idFolioOrigen=" + getIdFolioOrigen() +
            ", idFolioReferencia=" + getIdFolioReferencia() +
            "}";
    }
}
