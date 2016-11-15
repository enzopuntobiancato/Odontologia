package com.utn.tesis.model.odontograma;


public class TratamientoDeConducto extends HallazgoClinico {

    //CONSTRUCTORES
    public TratamientoDeConducto(){

    }

    protected TratamientoDeConducto(EstadoHallazgoClinico estado) {
        super(estado);
    }

    //GETTERS Y SETTERS
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
        return "tratamientoConducto";
    }
}
