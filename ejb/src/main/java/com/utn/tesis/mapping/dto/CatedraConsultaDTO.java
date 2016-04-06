package com.utn.tesis.mapping.dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 22/02/16
 * Time: 21:50
 */
public class CatedraConsultaDTO extends BaseDTO {
    private static final long serialVersionUID = 8358597624056880640L;

    private Long id;
    private String denominacion;
    private String descripcion;
    private Calendar fechaBaja;
    private String materia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
