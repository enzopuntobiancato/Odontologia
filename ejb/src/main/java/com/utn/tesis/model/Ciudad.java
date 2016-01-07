package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

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

    @NotNull
    @ManyToOne
    private Provincia provincia;

    @NotNull
    @Size(max = 50, message = "El nombre de la ciudad no puede ser mayor a 50 caracteres")
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

}
