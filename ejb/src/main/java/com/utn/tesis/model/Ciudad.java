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
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Ciudad extends EntityBase {

    @NotNull(message = "La provincia no puede ser nula.")
    @ManyToOne
    private Provincia provincia;

    @NotNull(message = "El nombre de la ciudad no puede ser nulo.")
    @Size(max = 50, message = "El nombre de la ciudad no puede ser mayor a 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    public Ciudad() {
    }

    public Ciudad(Provincia provincia, String nombre) {
        this.provincia = provincia;
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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
        if (!(o instanceof Ciudad)) return false;
        if (!super.equals(o)) return false;

        Ciudad ciudad = (Ciudad) o;

        if (nombre != null ? !nombre.equals(ciudad.nombre) : ciudad.nombre != null) return false;
        if (provincia != null ? !provincia.equals(ciudad.provincia) : ciudad.provincia != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (provincia != null ? provincia.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
