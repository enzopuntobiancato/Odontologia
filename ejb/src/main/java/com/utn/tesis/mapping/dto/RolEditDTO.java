package com.utn.tesis.mapping.dto;

import java.util.List;

public class RolEditDTO extends BaseDTO {
    private static final long serialVersionUID = -8760916536608812632L;

    private EnumDTO nombre;
    private List<PrivilegioDTO> privilegios;

    public EnumDTO getNombre() {
        return nombre;
    }

    public void setNombre(EnumDTO nombre) {
        this.nombre = nombre;
    }

    public List<PrivilegioDTO> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<PrivilegioDTO> privilegios) {
        this.privilegios = privilegios;
    }
}
