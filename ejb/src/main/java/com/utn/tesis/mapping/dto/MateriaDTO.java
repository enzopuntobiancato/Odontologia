package com.utn.tesis.mapping.dto;

import java.util.Calendar;

public class MateriaDTO extends BaseDTO {
    private static final long serialVersionUID = -1792766317112917917L;

    private String nombre;
    private EnumDTO nivel;
    private String descripcion;
    private String motivoBaja;
    private Calendar fechaBaja;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EnumDTO getNivel() {
        return nivel;
    }

    public void setNivel(EnumDTO nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}

