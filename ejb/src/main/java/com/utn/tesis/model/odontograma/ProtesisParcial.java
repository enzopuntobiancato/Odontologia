package com.utn.tesis.model.odontograma;

public class ProtesisParcial extends Protesis {

    //CONSTRUCTORES
    public ProtesisParcial() {

    }

    protected ProtesisParcial(EstadoHallazgoClinico estado) {
        super(estado);
    }

    //GETTERS Y SETTERS
    @Override
    public String getNombre() {
        return Terminology.PROTESIS_PARCIAL;
    }

    @Override
    public boolean isAplicaAPieza() {
        return true;
    }

    @Override
    public boolean isAplicaAPiezaGrupal() {
        return true;
    }

    @Override
    public String getMarkID() {
        return null;
    }
}
