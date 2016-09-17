package com.utn.tesis.mapping.dto;

import java.util.Calendar;

public class AtencionLightDTO extends BaseDTO {
    private static final long serialVersionUID = -4358655452720352770L;

    private Long id;
    private Calendar fechaAtencion;
    private String motivoConsultaOdontologica;
    private String descripcionProcedimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Calendar fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getMotivoConsultaOdontologica() {
        return motivoConsultaOdontologica;
    }

    public void setMotivoConsultaOdontologica(String motivoConsultaOdontologica) {
        this.motivoConsultaOdontologica = motivoConsultaOdontologica;
    }

    public String getDescripcionProcedimiento() {
        return descripcionProcedimiento;
    }

    public void setDescripcionProcedimiento(String descripcionProcedimiento) {
        this.descripcionProcedimiento = descripcionProcedimiento;
    }
}
