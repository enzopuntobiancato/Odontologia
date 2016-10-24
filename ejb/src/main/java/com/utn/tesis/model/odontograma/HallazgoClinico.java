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

    public boolean aplicaACara() {
        return false;
    }

    public boolean aplicaACaraCentral() {
        return false;
    }

    public boolean aplicaAPieza() {
        return false;
    }

    public boolean aplicaAPiezaGrupal() {
        return false;
    }

    public boolean aplicaAPosicion() {
        return false;
    }

    public List<Integer> posiciones() {
        return Collections.emptyList();
    }

    /**
     * Definimos el ID del "dibujo/marca" correspondiente del hallazgo
     * @return
     */
    public abstract String markID();
}
