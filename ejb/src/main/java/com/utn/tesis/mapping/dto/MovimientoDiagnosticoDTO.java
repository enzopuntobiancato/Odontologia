package com.utn.tesis.mapping.dto;

import java.util.Calendar;

public class MovimientoDiagnosticoDTO extends BaseDTO {
    private static final long serialVersionUID = 3201540644936580291L;

    private Long id;
    private AtencionLightDTO atencion;
    private EnumDTO estado;
    private Calendar fechaMovimiento;
    private String observaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtencionLightDTO getAtencion() {
        return atencion;
    }

    public void setAtencion(AtencionLightDTO atencion) {
        this.atencion = atencion;
    }

    public EnumDTO getEstado() {
        return estado;
    }

    public void setEstado(EnumDTO estado) {
        this.estado = estado;
    }

    public Calendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Calendar fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
