package com.utn.tesis.model.odontograma;

public class Extraccion extends HallazgoClinico {

    //CONSTRUCTORES
    public Extraccion(){

    }

    protected Extraccion(EstadoHallazgoClinico estado) {
        super(estado);
    }

    //GETTERS Y SETTERS

    @Override
    public String getNombre() {
        return Terminology.EXTRACCION;
    }

    @Override
    public boolean isAplicaAPieza() {
        return true;
    }

    @Override
    public String getMarkID() {
        return "extraccion";
    }
}
