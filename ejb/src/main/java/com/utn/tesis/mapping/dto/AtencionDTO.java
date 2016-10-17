package com.utn.tesis.mapping.dto;

import java.util.Calendar;

public class AtencionDTO extends BaseDTO {
    private static final long serialVersionUID = -4358655452720352770L;

    private Calendar fechaAtencion;
    private Calendar fechaDeCarga;
    private String descripcionProcedimiento;
    private boolean diagnosticoSolucionado;
    private String motivoNoSolucion;
    private AsignacionPacienteDTO asignacionPaciente;

    public Calendar getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Calendar fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Calendar getFechaDeCarga() {
        return fechaDeCarga;
    }

    public void setFechaDeCarga(Calendar fechaDeCarga) {
        this.fechaDeCarga = fechaDeCarga;
    }

    public String getDescripcionProcedimiento() {
        return descripcionProcedimiento;
    }

    public void setDescripcionProcedimiento(String descripcionProcedimiento) {
        this.descripcionProcedimiento = descripcionProcedimiento;
    }

    public AsignacionPacienteDTO getAsignacionPaciente() {
        return asignacionPaciente;
    }

    public void setAsignacionPaciente(AsignacionPacienteDTO asignacionPaciente) {
        this.asignacionPaciente = asignacionPaciente;
    }

    public boolean isDiagnosticoSolucionado() {
        return diagnosticoSolucionado;
    }

    public void setDiagnosticoSolucionado(boolean diagnosticoSolucionado) {
        this.diagnosticoSolucionado = diagnosticoSolucionado;
    }

    public String getMotivoNoSolucion() {
        return motivoNoSolucion;
    }

    public void setMotivoNoSolucion(String motivoNoSolucion) {
        this.motivoNoSolucion = motivoNoSolucion;
    }
}
