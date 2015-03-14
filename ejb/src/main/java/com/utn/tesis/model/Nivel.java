package com.utn.tesis.model;

import java.io.Serializable;

public enum Nivel implements Serializable {

    PRIMERO(0, "PRIMERO"),
    SEGUNDO(1, "SEGUNDO"),
    TERCERO(2, "TERCERO"),
    CUARTO(3, "CUARTO"),
    QUINTO(4, "QUINTO");

    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    Nivel(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
