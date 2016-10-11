package com.utn.tesis.model.odontograma;

public class Caries extends HallazgoClinico {
    @Override
    public String getNombre() {
        return Terminology.CARIES;
    }

    protected Caries(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public boolean aplicaACara() {
        return true;
    }

    @Override
    public String markID() {
        return null;
    }
}
