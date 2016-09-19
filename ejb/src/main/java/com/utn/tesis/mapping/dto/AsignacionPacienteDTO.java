package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.MovimientoAsignacionPaciente;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 7/08/16
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class AsignacionPacienteDTO extends BaseDTO {

    private String apellidoAlumno;
    private String nombreAlumno;
    private Long idAlumno;
    private Calendar fechaAsignacion;
    private Calendar fechaCreacion;
    private String apellidoPaciente;
    private String nombrePaciente;
    private String tipoDocumentoPaciente;
    private String numeroDocumentoPaciente;
    private String trabajoPracticoNombre;
    private Long id;

    private Long idPaciente;
    private AlumnoDTO alumno;
    private TrabajoPracticoDTO trabajoPractico;
    private Calendar fechaBaja;
    private String motivoBaja;
    private Diagnostico diagnostico;
    private ProfesorDTO autorizadoPor;
    private List<MovimientoAsignacionDTO> movimientoAsignacionPaciente;
    private MovimientoAsignacionDTO ultimoMovimiento;
    private String telefono;
    private String celular;
    private String email;

    //GETTERS AND SETTERS
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public MovimientoAsignacionDTO getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(MovimientoAsignacionDTO ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    public List<MovimientoAsignacionDTO> getMovimientoAsignacionPaciente() {
        return movimientoAsignacionPaciente;
    }

    public void setMovimientoAsignacionPaciente(List<MovimientoAsignacionDTO> movimientoAsignacionPaciente) {
        this.movimientoAsignacionPaciente = movimientoAsignacionPaciente;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrabajoPracticoNombre() {
        return trabajoPracticoNombre;
    }

    public void setTrabajoPracticoNombre(String trabajoPracticoNombre) {
        this.trabajoPracticoNombre = trabajoPracticoNombre;
    }


    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Calendar getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Calendar fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }

    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTipoDocumentoPaciente() {
        return tipoDocumentoPaciente;
    }

    public void setTipoDocumentoPaciente(String tipoDocumentoPaciente) {
        this.tipoDocumentoPaciente = tipoDocumentoPaciente;
    }

    public String getNumeroDocumentoPaciente() {
        return numeroDocumentoPaciente;
    }

    public void setNumeroDocumentoPaciente(String numeroDocumentoPaciente) {
        this.numeroDocumentoPaciente = numeroDocumentoPaciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }

    public TrabajoPracticoDTO getTrabajoPractico() {
        return trabajoPractico;
    }

    public void setTrabajoPractico(TrabajoPracticoDTO trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public ProfesorDTO getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(ProfesorDTO autorizadoPor) {
        this.autorizadoPor = autorizadoPor;
    }
}
