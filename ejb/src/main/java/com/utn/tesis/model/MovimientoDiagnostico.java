package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import io.github.benas.randombeans.annotation.Exclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(name = "movimientos_diagnostico")
public class MovimientoDiagnostico extends EntityBase {
    private static final long serialVersionUID = -5842583073152945171L;

    @Exclude
    @OneToOne
    @JoinColumn(name = "atencion_id")
    private Atencion atencion;

    @Exclude
    @NotNull(message = "El estado del movimiento diagnostico no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private EstadoDiagnostico estado;

    @NotNull(message = "La fecha del movimiento diagnostico no puede ser nula.")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_movimiento")
    private Calendar fechaMovimiento;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El usuario que genera el movimiento diagnostico no puede ser nulo.")
    @JoinColumn(name = "generado_por_id")
    private Usuario generadoPor;

    @Size(max = 400, message = "Las observaciones no pueden superar los 400 caracteres.")
    @Column(length = 400)
    private String observaciones;

    public MovimientoDiagnostico() {
        this.fechaMovimiento = Calendar.getInstance();
    }

    public MovimientoDiagnostico(Atencion atencion, EstadoDiagnostico estado, Calendar fechaMovimiento, Usuario generadoPor) {
        this.atencion = atencion;
        this.estado = estado;
        this.fechaMovimiento = fechaMovimiento;
        this.generadoPor = generadoPor;
    }

    public MovimientoDiagnostico(EstadoDiagnostico estado, Calendar fechaMovimiento, Usuario generadoPor) {
        this.estado = estado;
        this.fechaMovimiento = fechaMovimiento;
        this.generadoPor = generadoPor;
    }

    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public EstadoDiagnostico getEstado() {
        return estado;
    }

    public void setEstado(EstadoDiagnostico estado) {
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimientoDiagnostico)) return false;
        if (!super.equals(o)) return false;

        MovimientoDiagnostico that = (MovimientoDiagnostico) o;

        if (atencion != null ? !atencion.equals(that.atencion) : that.atencion != null) return false;
        if (estado != that.estado) return false;
        if (fechaMovimiento != null ? !fechaMovimiento.equals(that.fechaMovimiento) : that.fechaMovimiento != null)
            return false;
        if (generadoPor != null ? !generadoPor.equals(that.generadoPor) : that.generadoPor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (atencion != null ? atencion.hashCode() : 0);
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
