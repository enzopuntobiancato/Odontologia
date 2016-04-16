package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 6/03/16
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class TrabajoDTO extends BaseDTO {
    private String nombre;
    private Long id;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
