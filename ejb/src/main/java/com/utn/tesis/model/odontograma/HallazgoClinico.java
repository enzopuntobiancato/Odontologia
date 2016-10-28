package com.utn.tesis.model.odontograma;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class HallazgoClinico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String color;
    private EstadoHallazgoClinico estado;


    public HallazgoClinico() {
    }

    protected HallazgoClinico(EstadoHallazgoClinico estado) {
        this.estado = estado;
        this.color = estado.getColor();
        this.nombre = getNombre();
    }

    public abstract String getNombre();

    public String getColor() {
        return color;
    }

    public EstadoHallazgoClinico getEstado() {
        return estado;
    }

    public void setEstado(EstadoHallazgoClinico estado) {
        this.estado = estado;
        this.color = estado.getColor();
    }

    public boolean isAplicaACara() {
        return false;
    }

    public boolean isAplicaACaraCentral() {
        return false;
    }

    public boolean isAplicaAPieza() {
        return false;
    }

    public boolean isAplicaAPiezaGrupal() {
        return false;
    }

    public boolean isAplicaAPosicion() {
        return false;
    }

    public List<Integer> getPosiciones() {
        return Collections.emptyList();
    }

    /**
     * Definimos el ID del "dibujo/marca" correspondiente del hallazgo
     * @return
     */
    public abstract String getMarkID();
}
