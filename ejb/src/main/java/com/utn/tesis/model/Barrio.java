package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 07/01/16
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Barrio extends EntityBase {

    @NotNull(message = "La ciudad no puede ser nula.")
    @ManyToOne
    private Ciudad ciudad;

    @NotNull(message = "El nombre del barrio no puede ser nulo.")
    @Size(max = 70, message = "El nombre de la ciudad no puede ser mayor a 70 caracteres")
    @Column(nullable = false, length = 70)
    private String nombre;

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Barrio)) return false;
        if (!super.equals(o)) return false;

        Barrio barrio = (Barrio) o;

        if (ciudad != null ? !ciudad.equals(barrio.ciudad) : barrio.ciudad != null) return false;
        if (nombre != null ? !nombre.equals(barrio.nombre) : barrio.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ciudad != null ? ciudad.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
