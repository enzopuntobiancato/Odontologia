package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/02/16
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public class RolDTO extends BaseDTO {
    private Long id;
    private EnumDTO nombre;

    public RolDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumDTO getNombre() {
        return nombre;
    }

    public void setNombre(EnumDTO nombre) {
        this.nombre = nombre;
    }
}
