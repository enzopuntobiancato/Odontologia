package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import io.github.benas.randombeans.annotation.Exclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona extends SuperEntityBase {

    @TableGenerator(name = "PERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PERSON_GEN")
    private Long id;

    @Size(max = 75, message = "El apellido no puede ser mayor a 75 caracteres.")
    @NotNull(message = "Debe ingresar un apellido.")
    @Column(nullable = false, length = 75)
    private String apellido;

    @Size(max = 75, message = "El nombre no puede ser mayor a 75 caracteres")
    @NotNull(message = "Debe ingresar un nombre.")
    @Column(nullable = false, length = 75)
    private String nombre;

    @Embedded
    @NotNull(message = "El documento no puede ser nulo.")
    private Documento documento;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Calendar fechaNacimiento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull(message = "La fecha de carga no puede ser nula.")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_carga")
    private Calendar fechaCarga;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public Persona() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendar getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Calendar fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    /*
             * Calcula y devuelve un Integer con la edad de la persona.
             */
    public Integer getEdad() {
        return fechaNacimiento == null ? null : Calendar.getInstance().get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        if (!super.equals(o)) return false;

        Persona persona = (Persona) o;

        if (apellido != null ? !apellido.equals(persona.apellido) : persona.apellido != null) return false;
        if (documento != null ? !documento.equals(persona.documento) : persona.documento != null) return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(persona.fechaNacimiento) : persona.fechaNacimiento != null)
            return false;
        if (nombre != null ? !nombre.equals(persona.nombre) : persona.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (documento != null ? documento.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        return result;
    }
}
