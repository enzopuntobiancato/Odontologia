package com.utn.tesis.mapping.dto;

import java.util.Calendar;

public class BackupDTO extends BaseDTO {
    private static final long serialVersionUID = 6947181710482886461L;
    private String nombre;
    private Calendar fechaGeneracion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Calendar fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
