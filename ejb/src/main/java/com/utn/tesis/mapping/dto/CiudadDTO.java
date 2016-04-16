package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 18/02/16
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class CiudadDTO extends BaseDTO {
    private Long id;
    private String nombre;
    private ProvinciaDTO provincia;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

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
}
