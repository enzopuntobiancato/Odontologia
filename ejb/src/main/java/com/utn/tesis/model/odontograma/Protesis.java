package com.utn.tesis.model.odontograma;

public abstract class Protesis extends HallazgoClinico {

    public Protesis() {
    }

    protected Protesis(EstadoHallazgoClinico estado) {
        super(estado);
    }
}
