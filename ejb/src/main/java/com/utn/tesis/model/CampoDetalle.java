/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "CAMPODETALLE")
public class CampoDetalle extends DetalleHistoriaClinica {

    @Column(name = "detalle", length = 100)
    @Size(min = 1, max = 100, message = "La descripcion debe tener entre 1 y 100 caracteres o estar vacia.")
    private String only_detalle;

    public CampoDetalle() {
        super();
    }

    public CampoDetalle(String nombre, Integer grupo, Integer pregunta) {
        super(nombre, grupo, pregunta);
    }

    public String getOnly_detalle() {
        return only_detalle;
    }

    public void setOnly_detalle(String only_detalle) {
        this.only_detalle = only_detalle;
    }

}
