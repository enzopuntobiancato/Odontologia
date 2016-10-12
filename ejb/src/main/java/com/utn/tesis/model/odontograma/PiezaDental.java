package com.utn.tesis.model.odontograma;

import java.util.List;

public class PiezaDental {

    private String nombrePieza;
    private String nombreSector;
    private int numeroPieza;
    private int numeroSector;
    List<CaraPiezaDental> carasPiezaDental;
    private HallazgoClinico hallazgoClinico;

    public String getNombrePieza() {
        return nombrePieza;
    }

    public void setNombrePieza(String nombrePieza) {
        this.nombrePieza = nombrePieza;
    }

    public String getNombreSector() {
        return nombreSector;
    }

    public void setNombreSector(String nombreSector) {
        this.nombreSector = nombreSector;
    }

    public int getNumeroPieza() {
        return numeroPieza;
    }

    public void setNumeroPieza(int numeroPieza) {
        this.numeroPieza = numeroPieza;
    }

    public int getNumeroSector() {
        return numeroSector;
    }

    public void setNumeroSector(int numeroSector) {
        this.numeroSector = numeroSector;
    }

    public List<CaraPiezaDental> getCarasPiezaDental() {
        return carasPiezaDental;
    }

    public void setCarasPiezaDental(List<CaraPiezaDental> carasPiezaDental) {
        this.carasPiezaDental = carasPiezaDental;
    }

    public HallazgoClinico getHallazgoClinico() {
        return hallazgoClinico;
    }

    public void setHallazgoClinico(HallazgoClinico hallazgoClinico) {
        this.hallazgoClinico = hallazgoClinico;
    }
}
