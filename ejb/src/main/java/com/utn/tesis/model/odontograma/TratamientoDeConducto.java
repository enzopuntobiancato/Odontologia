package com.utn.tesis.model.odontograma;

public class TratamientoDeConducto extends HallazgoClinico {

    public TratamientoDeConducto(){

    }

    protected TratamientoDeConducto(EstadoHallazgoClinico estado) {
        super(estado);
    }

    @Override
    public String getNombre() {
        return Terminology.TRATAMIENTO_DE_CONDUCTO;
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
