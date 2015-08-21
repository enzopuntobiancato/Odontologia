package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 19/05/15
 * Time: 23:13
 */
@MappedSuperclass
public class Persona extends EntityBase {

    @Size(max = 150, message = "El apellido debe tener entre 1 y 150 caracteres.")
    @NotNull(message = "El apellido no puede estar vacío.")
    private String apellido;

    @Size(max = 150, message = "El nombre debe tener entre 1 y 150 caracteres")
    @NotNull(message = "El nombre no puede estar vacío.")
    private String nombre;

    @Embedded
    private Documento documento;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de nacimiento no puede estar vacío.")
    private Calendar fechaNacimiento;

    @OneToOne
    private Usuario usuario;

    public Persona() {
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

    /*
         * Calcula y devuelve un Integer con la edad de la persona.
         */
    public Integer getEdad() {
        return fechaNacimiento == null ? null : Calendar.getInstance().get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
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
