package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;

/**
 * Created by cids on 23/06/2015.
 */
@Entity
public class Funcionalidad extends EntityBase {

    private String nombre;
    private String estadoAsociado;
    private GrupoFuncionalidad grupoFuncionalidad;

    public Funcionalidad() {
    }

    public Funcionalidad(String nombre, String estadoAsociado, GrupoFuncionalidad grupoFuncionalidad) {
        this.nombre = nombre;
        this.estadoAsociado = estadoAsociado;
        this.grupoFuncionalidad = grupoFuncionalidad;
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

    public String getEstadoAsociado() {
        return estadoAsociado;
    }

    public void setEstadoAsociado(String estadoAsociado) {
        this.estadoAsociado = estadoAsociado;
    }

    public GrupoFuncionalidad getGrupoFuncionalidad() {
        return grupoFuncionalidad;
    }

    public void setGrupoFuncionalidad(GrupoFuncionalidad grupoFuncionalidad) {
        this.grupoFuncionalidad = grupoFuncionalidad;
    }
}
