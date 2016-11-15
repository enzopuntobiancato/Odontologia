package com.utn.tesis.model.odontograma;


import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PiezaDental implements Serializable {

    private static final long serialVersionUID = 1L;


    private String nombrePieza;
    private String nombreSector;
    private int numeroPieza;
    private int numeroSector;
    private List<CaraPiezaDental> carasPiezaDental;
    private HallazgoClinico hallazgoClinico;
    private Long diagnosticoId;

    //CONSTRUCTOR
    public PiezaDental() {
    }

    public PiezaDental(String nombrePieza, String nombreSector, int numeroSector,
                       int numeroPieza, List<CaraPiezaDental> carasPiezaDental) {
        this.nombrePieza = nombrePieza;
        this.nombreSector = nombreSector;
        this.numeroPieza = numeroPieza;
        this.numeroSector = numeroSector;
        this.carasPiezaDental = carasPiezaDental;
        this.hallazgoClinico = hallazgoClinico;
    }

    //GETTERS Y SETTERS
    public Long getDiagnosticoId() {
        return diagnosticoId;
    }

    public void setDiagnosticoId(Long diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
    }

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

    //METODOS
    public  Integer getNombrePiezaDental(){
        String nombrePieza = numeroSector +  "" + numeroPieza;
        return  Integer.valueOf(nombrePieza);
    }
}
