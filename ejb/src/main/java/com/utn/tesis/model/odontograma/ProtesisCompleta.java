package com.utn.tesis.model.odontograma;

public class ProtesisCompleta extends Protesis {
    protected ProtesisCompleta(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.PROTESIS_COMPLETA;
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
