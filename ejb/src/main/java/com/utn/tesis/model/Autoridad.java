package com.utn.tesis.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 28/08/15
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class Autoridad extends Persona {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "El cargo no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    public Autoridad() {
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
