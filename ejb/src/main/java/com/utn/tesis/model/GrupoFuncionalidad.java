package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;

/**
 * Created by cids on 23/06/2015.
 */
@Entity
public class GrupoFuncionalidad extends EntityBase {

    private String nombre;

    public GrupoFuncionalidad() {
    }

    public GrupoFuncionalidad(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void validar() throws SAPOValidationException {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
