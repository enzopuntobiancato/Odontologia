package com.utn.tesis.mapping.dto;

public class RolDTO extends BaseDTO {
    private EnumDTO nombre;

    public RolDTO() {
    }

    public EnumDTO getNombre() {
        return nombre;
    }

    public void setNombre(EnumDTO nombre) {
        this.nombre = nombre;
    }
}
