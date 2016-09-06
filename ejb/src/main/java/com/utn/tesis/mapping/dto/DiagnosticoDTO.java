package com.utn.tesis.mapping.dto;

import java.util.Calendar;
import java.util.List;

public class DiagnosticoDTO extends BaseDTO {
    private static final long serialVersionUID = 2698145564751713756L;

    private Long id;
    private Calendar fechaCreacion;
    private String observaciones;
    private PracticaOdontologicaDTO practicaOdontologica;
    private String practicaNoExistente;
    private List<MovimientoDiagnosticoDTO> movimientos;
    private MovimientoDiagnosticoDTO ultimoMovimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public PracticaOdontologicaDTO getPracticaOdontologica() {
        return practicaOdontologica;
    }

    public void setPracticaOdontologica(PracticaOdontologicaDTO practicaOdontologica) {
        this.practicaOdontologica = practicaOdontologica;
    }

    public String getPracticaNoExistente() {
        return practicaNoExistente;
    }

    public void setPracticaNoExistente(String practicaNoExistente) {
        this.practicaNoExistente = practicaNoExistente;
    }

    public List<MovimientoDiagnosticoDTO> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDiagnosticoDTO> movimientos) {
        this.movimientos = movimientos;
    }

    public MovimientoDiagnosticoDTO getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(MovimientoDiagnosticoDTO ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }
}
