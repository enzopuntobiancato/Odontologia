package com.utn.tesis.model.odontograma;

public class ProtesisCompleta extends Protesis {

    //CONTRUCTORES
    public ProtesisCompleta() {

    }

    protected ProtesisCompleta(EstadoHallazgoClinico estado) {
        super(estado);
    }
    //GETTERS Y SETTERS

    @Override
    public String getNombre() {
        return Terminology.PROTESIS_COMPLETA;
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
