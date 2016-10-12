package com.utn.tesis.model.odontograma;

public class ProtesisParcial extends Protesis {
    protected ProtesisParcial(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.PROTESIS_PARCIAL;
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
