package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 13:54
 */
public class FuncionalidadDTO extends BaseDTO {
    private static final long serialVersionUID = 8083638769020756453L;

    private Long id;
    private String nombre;
    private String estadoAsociado;
    private String grupoFuncionalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGrupoFuncionalidad() {
        return grupoFuncionalidad;
    }

    public void setGrupoFuncionalidad(String grupoFuncionalidad) {
        this.grupoFuncionalidad = grupoFuncionalidad;
    }
}
