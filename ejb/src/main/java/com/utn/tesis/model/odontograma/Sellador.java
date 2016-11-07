package com.utn.tesis.model.odontograma;

import java.util.Arrays;
import java.util.List;

public class Sellador extends HallazgoClinico {

    //CONSTRUCTORES
    public Sellador(){

    }

    protected Sellador(EstadoHallazgoClinico estado) {
        super(estado);
    }

    //GETTERS Y SETTES

    @Override
    public String getNombre() {
        return Terminology.SELLADOR;
    }

    @Override
    public boolean isAplicaACara() {
        return true;
    }

    @Override
    public boolean isAplicaACaraCentral() {
        return true;
    }

    @Override
    public boolean isAplicaAPosicion() {
        return true;
    }

    @Override
    public List<Integer> getPosiciones() {
        return null;
/*        return Arrays.asList(Integer.valueOf(4),
                Integer.valueOf(5),
                Integer.valueOf(6),
                Integer.valueOf(7));*/
    }

    @Override
    public String getMarkID() {
        return "sellador";
    }
}
