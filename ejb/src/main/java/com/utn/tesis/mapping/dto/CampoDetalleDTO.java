package com.utn.tesis.mapping.dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/05/16
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class CampoDetalleDTO extends DetalleHistoriaClinicaDTO {
    private String only_detalle;
    private Calendar fechaDetalle;

    //GETTERS AND SETTERS
    public String getOnly_detalle() {
        return only_detalle;
    }

    public void setOnly_detalle(String only_detalle) {
        this.only_detalle = only_detalle;
    }

    public Calendar getFechaDetalle() {
        return fechaDetalle;
    }

    public void setFechaDetalle(Calendar fechaDetalle) {
        this.fechaDetalle = fechaDetalle;
    }
}
