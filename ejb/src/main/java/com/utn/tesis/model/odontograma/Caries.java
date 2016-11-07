package com.utn.tesis.model.odontograma;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("caries")
public class Caries extends HallazgoClinico {

    //CONSTRUCTORES
    public Caries() {
    }

    protected Caries(EstadoHallazgoClinico estado) {
        super(estado);
    }
    //GETTERS Y SETERS
    @Override
    public String getNombre() {
        return Terminology.CARIES;
    }

    @Override
    public boolean isAplicaACara() {
        return true;
    }

    @Override
    public String getMarkID() {
        return "caries";
    }
}
