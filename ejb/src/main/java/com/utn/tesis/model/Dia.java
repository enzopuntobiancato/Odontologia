package com.utn.tesis.model;

public enum Dia {
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIÉRCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SÁBADO");

    String showableName;

    Dia(String showableName) {
        this.showableName = showableName;
    }

    public String getShowableName() {
        return showableName;
    }
}
