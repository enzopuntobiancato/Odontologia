/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import com.utn.tesis.util.FechaUtils;

import javax.persistence.MappedSuperclass;
import java.util.Calendar;

/**
 *
 * @author Maxi
 */
@MappedSuperclass
public abstract class Bajeable extends EntityBase {

    private int estadoAlta = ALTA;
    private String motivo;
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
        this.motivo = motivo;
        fechaBaja = FechaUtils.convertDateToCalendar(Calendar.getInstance().getTime());
    }

    public void darDeBaja(String motivo, Calendar fechaDeBaja) {
        estadoAlta = BAJA;
        this.motivo = motivo;
        fechaBaja = fechaDeBaja;
    }
}
