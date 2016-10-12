package com.utn.tesis.model.odontograma;

public class Corona extends Protesis {

    protected Corona(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.CORONA;
    }

    @Override
    public boolean aplicaAPieza() {
        return true;
    }

    @Override
    public String markID() {
        return null;
    }
}
