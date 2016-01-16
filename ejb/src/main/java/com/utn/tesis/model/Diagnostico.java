package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 14/01/16
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Diagnostico extends EntityBase {

    @NotNull(message = "La fecha de creacion del diagnostico no puede ser nula.")
    @Temporal(TemporalType.DATE)
    private Calendar fechaCreacion;

    @OneToMany(fetch = FetchType.LAZY)
    @NotNull(message = "El movimiento diagnostico no puede ser nulo.")
    private List<MovimientoDiagnostico> movimientoDiagnostico;

    @Size(max = 400, message = "La observacion del diagnostico no puede superar los 400 caracteres.")
    @Column(length = 400)
    private String observaciones;

    @NotNull
    private PracticaOdontologica practicaOdontologica;

    public Diagnostico() {
    }

    public Diagnostico(Calendar fechaCreacion, String observaciones, PracticaOdontologica practicaOdontologica) {
        this.fechaCreacion = fechaCreacion;
        this.observaciones = observaciones;
        this.practicaOdontologica = practicaOdontologica;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<MovimientoDiagnostico> getMovimientoDiagnostico() {
        return movimientoDiagnostico;
    }

    public void setMovimientoDiagnostico(List<MovimientoDiagnostico> movimientoDiagnostico) {
        this.movimientoDiagnostico = movimientoDiagnostico;
    }

    public boolean addMovimientoDiagnostico(MovimientoDiagnostico m) {
        if (movimientoDiagnostico == null || m == null) return false;
        return this.movimientoDiagnostico.add(m);
    }

    public boolean removeMovimientoDiagnostico(MovimientoDiagnostico m) {
        if (movimientoDiagnostico == null || m == null) return false;
        return this.movimientoDiagnostico.add(m);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public PracticaOdontologica getPracticaOdontologica() {
        return practicaOdontologica;
    }

    public void setPracticaOdontologica(PracticaOdontologica practicaOdontologica) {
        this.practicaOdontologica = practicaOdontologica;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
