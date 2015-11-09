package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:22
 */
public class EnumDTO extends BaseDTO {
    private static final long serialVersionUID = -6633220590140175202L;

    private String key;
    private String nombre;

    public EnumDTO() {
    }

    public EnumDTO(String key, String nombre) {
        this.key = key;
        this.nombre = nombre;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
