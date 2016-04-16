package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 18/02/16
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class ProvinciaDTO extends BaseDTO {
    private Long id;
    private String nombre;
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
