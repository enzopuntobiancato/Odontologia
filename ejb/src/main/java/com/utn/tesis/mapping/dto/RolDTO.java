package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/02/16
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
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
