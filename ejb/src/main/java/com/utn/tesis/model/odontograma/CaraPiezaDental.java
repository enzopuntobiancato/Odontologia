package com.utn.tesis.model.odontograma;

public class CaraPiezaDental {

    private String nombre;
    private int posicion;
    private HallazgoClinico hallazgoClinico;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public HallazgoClinico getHallazgoClinico() {
        return hallazgoClinico;
    }

    public void setHallazgoClinico(HallazgoClinico hallazgoClinico) {
        this.hallazgoClinico = hallazgoClinico;
    }
}
