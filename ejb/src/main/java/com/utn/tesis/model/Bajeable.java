/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.util.FechaUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.HashMap;

@MappedSuperclass
public abstract class Bajeable extends EntityBase {

    private int estadoAlta = ALTA;

    @Size(max = 150, message = "El motivo de baja debe tener entre 0 y 150 caracteres.")
    @Column(length = 150, name = "motivo_baja")
    private String motivoBaja;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_baja")
    private Calendar fechaBaja;

    public static int ALTA = 1;
    public static int BAJA = 0;

    public void darDeAlta() {
        estadoAlta = ALTA;
        fechaBaja = null;
        motivoBaja = null;
    }

    public void darDeBaja(String motivo) throws SAPOValidationException {
        if (estadoAlta == ALTA && motivoBaja == null && fechaBaja == null) {
            estadoAlta = BAJA;
            this.motivoBaja = motivo;
            fechaBaja = FechaUtils.convertDateToCalendar(Calendar.getInstance().getTime());
        } else {
            HashMap<String, String> errors = new HashMap<String, String>(1);
            errors.put("Entidad actualmente dada de baja", "La entidad ya se encuentra dada de baja.");
            throw new SAPOValidationException(errors);
        }

    }

    public void darDeBaja(String motivo, Calendar fechaDeBaja) throws SAPOValidationException {
        darDeBaja(motivo);
        fechaBaja = fechaDeBaja != null ? fechaDeBaja : this.fechaBaja;
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
