package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 21:58
 */
@Entity
public class Rol extends EntityBase {

    public static final String ADMIN = "ADMINISTRADOR";
    public static final String ALUMNO = "ALUMNO";
    public static final String PROFESOR = "PROFESOR";
    public static final String RESPONSABLE_RECEPCION_PACIENTES = "RESPONSABLE DE RECEPCIÓN DE PACIENTES";
    public static final String ADMIN_ACADEMICO = "ADMINISTRADOR ACADÉMICO";
    public static final String PACIENTE = "PACIENTE";
    public static final String AUTORIDAD = "AUTORIDAD";

    @NotNull
    @Size(max = 50, message = "El rol no puede tener mas de 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "rol")
    @NotNull(message = "Los privilegios no pueden ser nulos.")
    private List<Privilegio> privilegios;

    @Size(max = 250, message = "La descripcion del rol no puede tener mas de 250 caracteres")
    @Column(length = 250)
    private String descripcion;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonMap(view = JsonMap.Internal.class)
    public List<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        if (!super.equals(o)) return false;

        Rol rol = (Rol) o;

        if (nombre != null ? !nombre.equals(rol.nombre) : rol.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();

        if (privilegios == null) {
            e.put("Lista privilegios null", "La lista de privilegios no puede ser nula.");
        }

        if (privilegios.isEmpty()) {
            e.put("Lista privilegios vacia", "La lista de privilegios no puede estar vacia.");
        }

        if (!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

}
