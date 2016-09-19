package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.Paciente;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 30/08/16
 * Time: 1:20
 * To change this template use File | Settings | File Templates.
 */
public class DiagnosticoSupport {
    private Diagnostico diagnostico;
    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private Long idPaciente;

    //CONSTRUCTOR
    public DiagnosticoSupport(Diagnostico diagnostico, String apellido, String nombre, String tipoDocumento, String numeroDocumento, Long idPaciente) {
        this.diagnostico = diagnostico;
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idPaciente = idPaciente;
    }

    //GETTERS Y SETTERS
    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
}
