package com.utn.tesis.mapping.dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/05/16
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class CampoFechaDTO extends DetalleHistoriaClinicaDTO {
    private Calendar fecha;

    //GETTERS AND SETTERS
    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fechaDetalle) {
        this.fecha = fechaDetalle;
    }
}
