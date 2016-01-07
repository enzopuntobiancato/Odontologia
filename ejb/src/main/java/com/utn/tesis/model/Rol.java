package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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

    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy="rol")
    @NotNull(message = "Los privilegios no pueden ser nulos.")
    private List<Privilegio> privilegios;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
    private String descripcion;
    private String personaAsociada;

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

    @JsonMap(view = JsonMap.Internal.class)
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPersonaAsociada() {
        return personaAsociada;
    }

    public void setPersonaAsociada(String personaAsociada) {
        this.personaAsociada = personaAsociada;
    }

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();

        if(privilegios == null) {
            e.put("Lista privilegios null", "La lista de privilegios no puede ser nula.");
        }

        if(privilegios.isEmpty()) {
            e.put("Lista privilegios vacia", "La lista de privilegios no puede estar vacia.");
        }

        if(!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

}
