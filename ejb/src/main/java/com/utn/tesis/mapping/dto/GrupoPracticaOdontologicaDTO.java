package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 8/02/16
 * Time: 14:58
 */
public class GrupoPracticaOdontologicaDTO extends BaseDTO {
    private static final long serialVersionUID = 4992088903124341818L;

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
