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
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Atencion extends EntityBase {

    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "La fecha de atencion no puede ser nula.")
    private Calendar fechaAtencion;

    @Size(min = 1, max = 700, message = "El motivo de la consulta debe tener entre 1 y 700 caracteres")
    @NotNull(message = "El motivo de la consulta no puede ser nulo.")
    @Column(nullable = false, length = 700)
    private String motivoConsultaOdontologica;

    @Size(min = 1, max = 700, message = "El campo descripcion debe tener entre 1 y 700 caracteres")
    @NotNull(message = "La descripcion del procedimiento no puede ser nula.")
    @Column(nullable = false, length = 700)
    private String descripcionProcedimiento;

    @ManyToOne
    @JoinColumn(name = "AsignacionPaciente_id")
    private AsignacionPaciente asignacionPaciente;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "atencionDocumentacion_id")
    private List<Archivo> documentacion;

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
}
