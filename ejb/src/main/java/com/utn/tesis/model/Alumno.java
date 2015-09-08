package com.utn.tesis.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 28/08/15
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Alumno extends Persona {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Debe ingresar un legajo.")
    @Size(max = 10, message = "El legajo no puede ser mayor a 10 caracteres.")
    private String legajo;

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
}
