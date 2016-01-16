package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 07/01/16
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Provincia extends EntityBase {

    @NotNull
    @Size(max = 50, message = "El nombre de la provincia no puede ser mayor a 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    public Provincia() {
    }

    public Provincia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provincia)) return false;
        if (!super.equals(o)) return false;

        Provincia provincia = (Provincia) o;

        if (nombre != null ? !nombre.equals(provincia.nombre) : provincia.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
