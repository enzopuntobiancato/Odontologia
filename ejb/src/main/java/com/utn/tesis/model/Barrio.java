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
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Barrio extends EntityBase {

    @NotNull
    @ManyToOne
    private Ciudad ciudad;

    @NotNull
    @Size(max = 70, message = "El nombre de la ciudad no puede ser mayor a 70 caracteres")
    private String nombre;

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
