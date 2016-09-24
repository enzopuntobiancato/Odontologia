package com.utn.tesis.mapping.dto;

public class CiudadDTO extends BaseDTO {
    private String nombre;
    private ProvinciaDTO provincia;

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
