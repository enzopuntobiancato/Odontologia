package com.utn.tesis.model;

import javax.persistence.Column;
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

    @NotNull(message = "El legajo del alumno no puede ser nulo.")
    @Size(max = 10, message = "El legajo no puede ser mayor a 10 caracteres.")
    @Column(nullable = false, length = 10)
    private String legajo;

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno)) return false;
        if (!super.equals(o)) return false;

        Alumno alumno = (Alumno) o;

        if (legajo != null ? !legajo.equals(alumno.legajo) : alumno.legajo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (legajo != null ? legajo.hashCode() : 0);
        return result;
    }
}
