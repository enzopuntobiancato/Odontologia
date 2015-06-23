package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(name = "rol_x_privilegio",
            joinColumns = {
                    @JoinColumn(name = "rol_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "privilegio_id")})
    private List<Privilegio> privilegios;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}