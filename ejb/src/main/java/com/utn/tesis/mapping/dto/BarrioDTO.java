package com.utn.tesis.mapping.dto;

public class BarrioDTO extends BaseDTO {
    private String nombre;
    private CiudadDTO ciudad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CiudadDTO getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadDTO ciudad) {
        this.ciudad = ciudad;
    }
}
