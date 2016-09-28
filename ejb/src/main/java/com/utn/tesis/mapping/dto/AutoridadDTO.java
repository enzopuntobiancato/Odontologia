package com.utn.tesis.mapping.dto;

public class AutoridadDTO extends PersonaDTO {
    private static final long serialVersionUID = -8418612671908394038L;

    private EnumDTO cargo;

    public EnumDTO getCargo() {
        return cargo;
    }

    public void setCargo(EnumDTO cargo) {
        this.cargo = cargo;
    }
}
