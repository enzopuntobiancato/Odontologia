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
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class AsignacionPaciente extends EntityBase {


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El alumno de la asignacion no puede ser nulo.")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profesor autorizadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El diagnostico de la asignacion no puede ser nulo.")
    private Diagnostico diagnostico;

    @NotNull(message = "La fecha de asignacion no puede ser nula.")
    @Temporal(TemporalType.DATE)
    private Calendar fechaAsignacion;

    @NotNull(message = "La fecha de creacion de la asignacion no puede ser nula.")
    @Temporal(TemporalType.DATE)
    private Calendar fechaCreacion;

    @Size(max = 300, message = "El motivo de cancelacion no puede tener mas de 300 caracteres.")
    @Column(length = 300)
    private String motivoCancelacion;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignacionPaciente_id")
    @NotNull(message = "El movimiento de asignacion no puede ser nulo.")
    private List<MovimientoAsignacionPaciente> movimientoAsignacionPaciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El trabajo practico de la asignacion no puede ser nulo.")
    private TrabajoPractico trabajoPractico;

    public AsignacionPaciente() {
        this.fechaCreacion = Calendar.getInstance();
    }

    public AsignacionPaciente(Alumno alumno, Profesor autorizadoPor, Diagnostico diagnostico, Calendar fechaAsignacion, TrabajoPractico trabajoPractico) {
        this();
        this.alumno = alumno;
        this.autorizadoPor = autorizadoPor;
        this.diagnostico = diagnostico;
        this.fechaAsignacion = fechaAsignacion;
        this.trabajoPractico = trabajoPractico;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Profesor getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(Profesor autorizadoPor) {
        this.autorizadoPor = autorizadoPor;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Calendar getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Calendar fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public List<MovimientoAsignacionPaciente> getMovimientoAsignacionPaciente() {
        return movimientoAsignacionPaciente;
    }

    public void setMovimientoAsignacionPaciente(List<MovimientoAsignacionPaciente> movimientoAsignacionPaciente) {
        this.movimientoAsignacionPaciente = movimientoAsignacionPaciente;
    }

    public boolean addMovimientoAsignacionPaciente(MovimientoAsignacionPaciente m) {
        if (movimientoAsignacionPaciente == null || m == null) return false;
        return this.movimientoAsignacionPaciente.add(m);
    }

    public boolean removeMovimientoAsignacionPaciente(MovimientoAsignacionPaciente m) {
        if (movimientoAsignacionPaciente == null || m == null) return false;
        return this.movimientoAsignacionPaciente.remove(m);
    }

    public TrabajoPractico getTrabajoPractico() {
        return trabajoPractico;
    }

    public void setTrabajoPractico(TrabajoPractico trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignacionPaciente)) return false;
        if (!super.equals(o)) return false;

        AsignacionPaciente that = (AsignacionPaciente) o;

        if (alumno != null ? !alumno.equals(that.alumno) : that.alumno != null) return false;
        if (autorizadoPor != null ? !autorizadoPor.equals(that.autorizadoPor) : that.autorizadoPor != null)
            return false;
        if (diagnostico != null ? !diagnostico.equals(that.diagnostico) : that.diagnostico != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (trabajoPractico != null ? !trabajoPractico.equals(that.trabajoPractico) : that.trabajoPractico != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (alumno != null ? alumno.hashCode() : 0);
        result = 31 * result + (autorizadoPor != null ? autorizadoPor.hashCode() : 0);
        result = 31 * result + (diagnostico != null ? diagnostico.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (trabajoPractico != null ? trabajoPractico.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}