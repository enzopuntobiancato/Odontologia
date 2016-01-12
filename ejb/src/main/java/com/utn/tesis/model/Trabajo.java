package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 14/01/16
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Trabajo extends EntityBase {

    @NotNull(message = "El nombre del trabajo no puede ser nulo.")
    @Size(max = 50, message = "El nombre del trabajo no puede superar los 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nombre;

    public Trabajo() {
    }

    public Trabajo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trabajo)) return false;
        if (!super.equals(o)) return false;

        Trabajo trabajo = (Trabajo) o;

        if (nombre != null ? !nombre.equals(trabajo.nombre) : trabajo.nombre != null) return false;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
