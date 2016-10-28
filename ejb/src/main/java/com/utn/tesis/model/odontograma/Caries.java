package com.utn.tesis.model.odontograma;

public class Caries extends HallazgoClinico {

    public Caries() {
    }

    @Override
    public String getNombre() {
        return Terminology.CARIES;
    }

    protected Caries(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public boolean isAplicaACara() {
        return true;
    }

    @Override
    public String getMarkID() {
        return null;
    }
}
