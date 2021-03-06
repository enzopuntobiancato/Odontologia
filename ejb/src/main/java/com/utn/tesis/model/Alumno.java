package com.utn.tesis.model;

import io.github.benas.randombeans.annotation.Exclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "alumnos")
public class Alumno extends Persona {
    private static final long serialVersionUID = -2397655902394714260L;

    @Exclude
    @Size(max = 10, message = "El legajo no puede ser mayor a 10 caracteres.")
    @Column(length = 10)
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
