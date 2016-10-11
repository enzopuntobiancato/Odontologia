package com.utn.tesis.model.odontograma;

public class Extraccion extends HallazgoClinico {
    protected Extraccion(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.EXTRACCION;
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
