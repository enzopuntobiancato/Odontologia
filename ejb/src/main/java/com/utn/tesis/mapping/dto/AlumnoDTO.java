package com.utn.tesis.mapping.dto;

public class AlumnoDTO extends PersonaDTO {
    private static final long serialVersionUID = -4693060504635879254L;

    private String legajo;

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
}
