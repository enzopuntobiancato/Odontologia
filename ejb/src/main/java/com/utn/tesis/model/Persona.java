package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

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

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Alumno.class, name = "com.utn.tesis.Alumno"),
        @JsonSubTypes.Type(value = Profesor.class, name = "com.utn.tesis.Profesor"),
        @JsonSubTypes.Type(value = ResponsableRecepcion.class, name = "com.utn.tesis.ResponsableRecepcion"),
        @JsonSubTypes.Type(value = Autoridad.class, name = "com.utn.tesis.Autoridad"),
        @JsonSubTypes.Type(value = Persona.class, name = "com.utn.tesis.Persona")})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona extends EntityBase {
    private static final long serialVersionUID = 1L;

    @Transient
    private String type;

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
    @NotNull(message = "Debe ingresar una fecha de nacimiento.")
    private Calendar fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @NotNull(message = "La fecha de carga no puede ser nula.")
    @Temporal(TemporalType.DATE)
    private Calendar fechaCarga;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;


    public Persona() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonMap(view = JsonMap.Public.class)
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @JsonMap(view = JsonMap.Public.class)
    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @JsonMap(view = JsonMap.Internal.class)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonMap(view = JsonMap.Public.class)
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
