package com.utn.tesis.model.odontograma;

public class Puente extends HallazgoClinico {

    protected Puente(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.PUENTE;
    }

    @Override
    public boolean aplicaAPieza() {
        return true;
    }

    @Override
    public boolean aplicaAPiezaGrupal() {
        return true;
    }

    @Override
    public String markID() {
        return null;
    }
}
