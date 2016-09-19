package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.Paciente;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 7/08/16
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class AsignacionPacienteEditCreateDTO extends BaseDTO {

    private AlumnoDTO alumno;
    private ProfesorDTO autorizadoPor;
    private Diagnostico diagnostico;
    private Calendar fechaAsignacion;
    private String motivoCancelacion;
    private TrabajoPracticoDTO trabajoPractico;
    private Long pacienteId;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }

    public ProfesorDTO getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(ProfesorDTO autorizadoPor) {
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

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public TrabajoPracticoDTO getTrabajoPractico() {
        return trabajoPractico;
    }

    public void setTrabajoPractico(TrabajoPracticoDTO trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
