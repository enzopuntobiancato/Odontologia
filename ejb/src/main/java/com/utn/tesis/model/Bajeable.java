/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import com.utn.tesis.util.FechaUtils;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.util.Calendar;

/**
 *
 * @author Maxi
 */
@MappedSuperclass
public abstract class Bajeable extends EntityBase {

    private int estadoAlta = ALTA;

    @Size(max = 150, message = "El motivoBaja de baja debe tener entre 0 y 150 caracteres.")
    private String motivoBaja;

    private Calendar fechaBaja;

    private static int ALTA = 1;
    private static int BAJA = 0;
    
    public void darDeAlta(){ 
        estadoAlta = ALTA;
        fechaBaja = null;
    }
    
    private void darDeBaja() {
        estadoAlta = ALTA;
    }
    
    public void darDeBaja(String motivo) {
        estadoAlta = BAJA;
        this.motivoBaja = motivo;
        fechaBaja = FechaUtils.convertDateToCalendar(Calendar.getInstance().getTime());
    }

    public void darDeBaja(String motivo, Calendar fechaDeBaja) {
        estadoAlta = BAJA;
        this.motivoBaja = motivo;
        fechaBaja = fechaDeBaja;
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
