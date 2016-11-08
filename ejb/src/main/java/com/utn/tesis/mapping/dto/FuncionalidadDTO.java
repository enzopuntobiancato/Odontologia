package com.utn.tesis.mapping.dto;

public class FuncionalidadDTO extends BaseDTO {
    private static final long serialVersionUID = 8083638769020756453L;

    private String nombre;
    private String estadoAsociado;
    private String grupoFuncionalidad;
    private boolean esItemMenu;

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

    public String getGrupoFuncionalidad() {
        return grupoFuncionalidad;
    }

    public void setGrupoFuncionalidad(String grupoFuncionalidad) {
        this.grupoFuncionalidad = grupoFuncionalidad;
    }

    public boolean isEsItemMenu() {
        return esItemMenu;
    }

    public void setEsItemMenu(boolean esItemMenu) {
        this.esItemMenu = esItemMenu;
    }
}
