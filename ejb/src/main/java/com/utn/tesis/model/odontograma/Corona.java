package com.utn.tesis.model.odontograma;

public class Corona extends Protesis {

    //CONSTRUCTORES
    public Corona() {
    }

    protected Corona(EstadoHallazgoClinico estado) {
        super(estado);
    }

    //GETTERS Y SETTERS
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
        return "corona";
    }
}
