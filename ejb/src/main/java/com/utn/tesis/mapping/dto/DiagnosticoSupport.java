package com.utn.tesis.mapping.dto;



import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 30/08/16
 * Time: 1:20
 * To change this template use File | Settings | File Templates.
 */
public class DiagnosticoSupport {
    private Long idDiagnostico;
    private Calendar fechaCreacion;
    private String denominacionPractica;
    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String trabajoPractico;
    private Long idPaciente;

    //CONSTRUCTOR

    public DiagnosticoSupport(Long idDiagnostico, Calendar fechaCreacion, String denominacionPractica, String apellido,
                              String nombre, String tipoDocumento, String numeroDocumento, Long idPaciente, String trabajoPractico) {
        this.idDiagnostico = idDiagnostico;
        this.fechaCreacion = fechaCreacion;
        this.denominacionPractica = denominacionPractica;
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idPaciente = idPaciente;
        this.trabajoPractico = trabajoPractico;
    }


    //GETTERS Y SETTERS
    public String getTrabajoPractico() {
        return trabajoPractico;
    }

    public void setTrabajoPractico(String trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
    }

    public Long getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Long idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDenominacionPractica() {
        return denominacionPractica;
    }

    public void setDenominacionPractica(String denominacionPractica) {
        this.denominacionPractica = denominacionPractica;
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
