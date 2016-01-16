package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 14/01/16
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class MovimientoAsignacionPaciente extends EntityBase {

    @NotNull(message = "El estado del movimiento de la asignacion del paciente no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private EstadoAsignacionPaciente estado;

    @NotNull(message = "La fecha del movimiento de asignacion no puede ser nula.")
    @Temporal(TemporalType.DATE)
    private Calendar fechaMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El usuario que genera el movimiento de asignacion no puede ser nulo.")
    private Usuario generadoPor;

    public MovimientoAsignacionPaciente() {
    }

    public MovimientoAsignacionPaciente(EstadoAsignacionPaciente estado, Calendar fechaMovimiento, Usuario generadoPor) {
        this.estado = estado;
        this.fechaMovimiento = fechaMovimiento;
        this.generadoPor = generadoPor;
    }

    public EstadoAsignacionPaciente getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignacionPaciente estado) {
        this.estado = estado;
    }

    public Calendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Calendar fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Usuario getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(Usuario generadoPor) {
        this.generadoPor = generadoPor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimientoAsignacionPaciente)) return false;
        if (!super.equals(o)) return false;

        MovimientoAsignacionPaciente that = (MovimientoAsignacionPaciente) o;

        if (estado != that.estado) return false;
        if (fechaMovimiento != null ? !fechaMovimiento.equals(that.fechaMovimiento) : that.fechaMovimiento != null)
            return false;
        if (generadoPor != null ? !generadoPor.equals(that.generadoPor) : that.generadoPor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (fechaMovimiento != null ? fechaMovimiento.hashCode() : 0);
        result = 31 * result + (generadoPor != null ? generadoPor.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
