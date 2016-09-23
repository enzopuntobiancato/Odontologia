package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "asignaciones_paciente")
public class AsignacionPaciente extends EntityBase{
    private static final long serialVersionUID = -678479853171993357L;
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    @NotNull(message = "El alumno de la asignacion no puede ser nulo.")
    private Alumno alumno;
    @ManyToOne
    @JoinColumn(name = "autorizado_por_id")
    private Profesor autorizadoPor;
    @ManyToOne
    @JoinColumn(name = "diagnostico_id")
    @NotNull(message = "El diagnostico de la asignacion no puede ser nulo.")
    private Diagnostico diagnostico;
    @NotNull(message = "La fecha de asignacion no puede ser nula.")
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.DATE)
    private Calendar fechaAsignacion;
    @NotNull(message = "La fecha de creacion de la asignacion no puede ser nula.")
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Calendar fechaCreacion;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ultimo_movimiento_id")
    private MovimientoAsignacionPaciente ultimoMovimiento;
    /*    @Size(max = 300, message = "El motivo de cancelacion no puede tener mas de 300 caracteres.")
        @Column(length = 300, name = "motivo_cancelacion")
        private String motivoCancelacion;*/
    @JoinColumn(name = "asignacion_paciente_id")
    @NotNull(message = "El movimiento de asignacion no puede ser nulo.")
    @OneToMany(fetch = FetchType.LAZY)
    @Size(min = 1)
    private List<MovimientoAsignacionPaciente> movimientoAsignacionPaciente;
    @ManyToOne
    @JoinColumn(name = "trabajo_practico_id")
    @NotNull(message = "El trabajo practico de la asignacion no puede ser nulo.")
    private TrabajoPractico trabajoPractico;
    @OneToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente de la asignacion no puede ser nulo.")
    private Paciente paciente;

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

    /*  public String getMotivoCancelacion() {
          return motivoCancelacion;
      }

      public void setMotivoCancelacion(String motivoCancelacion) {
          this.motivoCancelacion = motivoCancelacion;
      }
  */
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public MovimientoAsignacionPaciente getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(MovimientoAsignacionPaciente ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    /* public Calendar getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Calendar fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignacionPaciente)) return false;
        if (!super.equals(o)) return false;

        AsignacionPaciente that = (AsignacionPaciente) o;

        if (alumno != null ? !alumno.equals(that.alumno) : that.alumno != null) return false;
        if (paciente != null ? !paciente.equals(that.paciente) : that.paciente != null) return false;
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

    }
}