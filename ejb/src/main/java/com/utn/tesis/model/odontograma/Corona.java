package com.utn.tesis.model.odontograma;

public class Corona extends Protesis {

    public Corona() {
    }

    protected Corona(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.CORONA;
    }

    @Override
    public boolean isAplicaAPieza() {
        return true;
    }

    @Override
    public String getMarkID() {
        return null;
    }
}
