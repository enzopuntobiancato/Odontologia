package com.utn.tesis.model.odontograma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaraPiezaDental implements Serializable {

    private static final long serialVersionUID = 1L;


    private String nombre;
    private int posicion;
    private HallazgoClinico hallazgoClinico;

    public CaraPiezaDental() {
    }

    public CaraPiezaDental(String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

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

    public static List<CaraPiezaDental> createDefault(String uno, String dos, String tres, String cuatro, String central) {
        List<CaraPiezaDental> cpd = new ArrayList<CaraPiezaDental>();
        cpd.add(new CaraPiezaDental(uno, 1));
        cpd.add(new CaraPiezaDental(dos, 2));
        cpd.add(new CaraPiezaDental(tres, 3));
        cpd.add(new CaraPiezaDental(cuatro, 4));
        cpd.add(new CaraPiezaDental(central, 5));
    return cpd;
    }
}
