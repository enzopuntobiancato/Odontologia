package com.utn.tesis.model.odontograma;

public class Puente extends HallazgoClinico {

    public Puente() {

    }
    protected Puente(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.PUENTE;
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
