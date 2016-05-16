package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "atenciones")
public class Atencion extends EntityBase {
    private static final long serialVersionUID = 8494086048433594969L;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de atencion no puede ser nula.")
    @Column(name = "fecha_atencion")
    private Calendar fechaAtencion;

    @Size(min = 1, max = 700, message = "El motivo de la consulta debe tener entre 1 y 700 caracteres")
    @NotNull(message = "El motivo de la consulta no puede ser nulo.")
    @Column(nullable = false, length = 700, name = "motivo_consulta_odontologica")
    private String motivoConsultaOdontologica;

    @Size(min = 1, max = 700, message = "El campo descripcion debe tener entre 1 y 700 caracteres")
    @NotNull(message = "La descripcion del procedimiento no puede ser nula.")
    @Column(nullable = false, length = 700, name = "descripcion_procedimiento")
    private String descripcionProcedimiento;

    @ManyToOne
    @JoinColumn(name = "asignacion_paciente_id")
    private AsignacionPaciente asignacionPaciente;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "atencion_id")
    private List<Archivo> documentaciones;

    public Atencion() {
    }

    public Atencion(Calendar fechaAtencion, String motivoConsultaOdontologica, String descripcionProcedimiento, AsignacionPaciente asignacionPaciente) {
        this.fechaAtencion = fechaAtencion;
        this.motivoConsultaOdontologica = motivoConsultaOdontologica;
        this.descripcionProcedimiento = descripcionProcedimiento;
        this.asignacionPaciente = asignacionPaciente;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
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

    public AsignacionPaciente getAsignacionPaciente() {
        return asignacionPaciente;
    }

    public void setAsignacionPaciente(AsignacionPaciente asignacionPaciente) {
        this.asignacionPaciente = asignacionPaciente;
    }

    public List<Archivo> getDocumentaciones() {
        return documentaciones;
    }

    public void setDocumentaciones(List<Archivo> documentaciones) {
        this.documentaciones = documentaciones;
    }
}
