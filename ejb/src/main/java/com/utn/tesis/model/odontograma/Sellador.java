package com.utn.tesis.model.odontograma;

import java.util.Arrays;
import java.util.List;

public class Sellador extends HallazgoClinico {

    public Sellador(){

    }

    protected Sellador(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.SELLADOR;
    }

    @Override
    public boolean aplicaACara() {
        return true;
    }

    @Override
    public boolean aplicaACaraCentral() {
        return true;
    }

    @Override
    public boolean aplicaAPosicion() {
        return true;
    }

    @Override
    public List<Integer> posiciones() {
        return Arrays.asList(Integer.valueOf(4),
                Integer.valueOf(5),
                Integer.valueOf(6),
                Integer.valueOf(7));
    }

    @Override
    public String markID() {
        return null;
    }
}
