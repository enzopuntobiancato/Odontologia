package com.utn.tesis.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 28/08/15
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class Autoridad extends Persona {
    private static final long serialVersionUID = 1L;

    private String legajo;
    @NotNull(message = "Debe ingresar un cargo.")
    @Size(max = 50, message = "El cargo no puede ser mayor a 50 caracteres.")
    private String cargo;

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
