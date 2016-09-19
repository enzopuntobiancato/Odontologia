package com.utn.tesis.mapping.dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 10/09/16
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class MovimientoAsignacionDTO extends BaseDTO {
    private EnumDTO estado;
    private UsuarioDTO generadoPor;
    private Calendar fechaMovimiento;

    //GETTERS AND SETTERS
    public UsuarioDTO getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(UsuarioDTO generadoPor) {
        this.generadoPor = generadoPor;
    }

    public Calendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Calendar fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public EnumDTO getEstado() {
        return estado;
    }

    public void setEstado(EnumDTO estado) {
        this.estado = estado;
    }
}
