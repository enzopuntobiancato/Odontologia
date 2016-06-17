/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Calendar;

/**
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "CAMPOFECHA")
public class CampoFecha extends DetalleHistoriaClinica {

    @Column(name = "fecha")
    private Calendar fecha;

    public CampoFecha() {
        super();
    }

    public CampoFecha(String nombre, Integer grupo, Integer pregunta) {
        super(nombre, grupo, pregunta);
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampoFecha)) return false;
        if (!super.equals(o)) return false;

        CampoFecha that = (CampoFecha) o;

        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        return result;
    }
}
