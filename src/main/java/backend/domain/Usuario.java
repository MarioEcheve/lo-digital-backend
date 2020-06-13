package backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "rut", nullable = false)
    private String rut;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 100)
    @Column(name = "apellido_paterno", length = 100)
    private String apellidoPaterno;

    @Size(max = 100)
    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Size(max = 80)
    @Column(name = "profesion_oficio", length = 80)
    private String profesionOficio;

    @Size(max = 50)
    @Column(name = "email_principal", length = 50)
    private String emailPrincipal;

    @Size(max = 50)
    @Column(name = "email_secundario", length = 50)
    private String emailSecundario;

    @Size(max = 50)
    @Column(name = "telefono_principal", length = 50)
    private String telefonoPrincipal;

    @Size(max = 50)
    @Column(name = "telefono_secundario", length = 50)
    private String telefonoSecundario;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioDependencia> usuarioDependencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public Usuario rut(String rut) {
        this.rut = rut;
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Usuario apellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public Usuario apellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getProfesionOficio() {
        return profesionOficio;
    }

    public Usuario profesionOficio(String profesionOficio) {
        this.profesionOficio = profesionOficio;
        return this;
    }

    public void setProfesionOficio(String profesionOficio) {
        this.profesionOficio = profesionOficio;
    }

    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    public Usuario emailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
        return this;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public Usuario emailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
        return this;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    public String getTelefonoPrincipal() {
        return telefonoPrincipal;
    }

    public Usuario telefonoPrincipal(String telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
        return this;
    }

    public void setTelefonoPrincipal(String telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
    }

    public String getTelefonoSecundario() {
        return telefonoSecundario;
    }

    public Usuario telefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
        return this;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Usuario fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Usuario fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<UsuarioDependencia> getUsuarioDependencias() {
        return usuarioDependencias;
    }

    public Usuario usuarioDependencias(Set<UsuarioDependencia> usuarioDependencias) {
        this.usuarioDependencias = usuarioDependencias;
        return this;
    }

    public Usuario addUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.add(usuarioDependencia);
        usuarioDependencia.setUsuario(this);
        return this;
    }

    public Usuario removeUsuarioDependencia(UsuarioDependencia usuarioDependencia) {
        this.usuarioDependencias.remove(usuarioDependencia);
        usuarioDependencia.setUsuario(null);
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
        if (!(o instanceof Usuario)) {
            return false;
        }
        return id != null && id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", rut='" + getRut() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", profesionOficio='" + getProfesionOficio() + "'" +
            ", emailPrincipal='" + getEmailPrincipal() + "'" +
            ", emailSecundario='" + getEmailSecundario() + "'" +
            ", telefonoPrincipal='" + getTelefonoPrincipal() + "'" +
            ", telefonoSecundario='" + getTelefonoSecundario() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
