package com.utn.tesis.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "autoridades")
public class Autoridad extends Persona {
    private static final long serialVersionUID = -887497634688613227L;

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
