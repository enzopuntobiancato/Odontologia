package com.utn.tesis.model.odontograma;

public enum EstadoHallazgoClinico {
    REALIZADO("red"),
    PENDIENTE("blue"),
    REHACER("purple");

    private String color;

    EstadoHallazgoClinico(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
