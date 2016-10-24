package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.util.FechaUtils;
import io.github.benas.randombeans.annotation.Exclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.HashMap;

@Entity
@Table(name = "pacientes")
public class Paciente extends Persona implements IBajeable {
    private static final long serialVersionUID = -836105309387103995L;

    @Transient
    private int estadoAlta = Bajeable.ALTA;

    @Size(max = 30, message = "El celular no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String celular;

    @Size(max = 50, message = "El email no puede tener mas de 50 caracteres.")
    @Column(length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "archivo_imagen_id")
    private Archivo imagen;

    @Exclude
    @ManyToOne
    @JoinColumn(name = "lugar_nacimiento_ciudad_id")
    private Ciudad lugarDeNacimiento;

    @Size(max = 50, message = "El nombre del medico de cabecera no puede tener mas de 50 caracteres.")
    @Column(length = 50, name = "medico_cabecera")
    private String medicoCabecera;

    @Enumerated(EnumType.STRING)
    @Column(name = "nacionalidad")
    private Nacionalidad nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_estudio")
    private NivelEstudio nivelEstudio;

    @Exclude
    @ManyToOne
    @JoinColumn(name = "obra_social_id")
    private ObraSocial obraSocial;

    @Size(max = 30, message = "El campo religion no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String religion;

    @Size(max = 30, message = "El servicio de emergencia no puede tener mas de 30 caracteres.")
    @Column(length = 30, name = "servicio_emergencia")
    private String servicioEmergencia;

    @Size(max = 30, message = "El telefono no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String telefono;

    @Size(max = 30, message = "El telefono del medico de cabecera no puede tener mas de 30 caracteres.")
    @Column(length = 30, name = "medico_cabecera_telefono")
    private String telefonoMedicoCabecera;

    @Exclude
    @ManyToOne
    @JoinColumn(name = "trabajo_id")
    private Trabajo trabajo;

    @Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @Exclude
    @OneToOne
    @JoinColumn(name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    @Size(max = 150, message = "El motivo de baja debe tener entre 0 y 150 caracteres.")
    @Column(length = 150, name = "motivo_baja")
    @Exclude
    private String motivoBaja;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_baja")
    @Exclude
    private Calendar fechaBaja;

    @Column(name="privado_liberdad")
    private boolean privadoLibertad;

    @Size(max = 30, message = "El lugar donde el paciente está privado de la libertad no puede superar los 30 caracteres.")
    @Column(name="privado_liberdad_donde", length = 30)
    private String privadoLibertadDonde;

    public Paciente() {
        historiaClinica = new HistoriaClinica();
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
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

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Archivo getImagen() {
        return imagen;
    }

    public void setImagen(Archivo imagen) {
        this.imagen = imagen;
    }

    public Ciudad getLugarDeNacimiento() {
        return lugarDeNacimiento;
    }

    public void setLugarDeNacimiento(Ciudad lugarDeNacimiento) {
        this.lugarDeNacimiento = lugarDeNacimiento;
    }

    public String getMedicoCabecera() {
        return medicoCabecera;
    }

    public void setMedicoCabecera(String medicoCabecera) {
        this.medicoCabecera = medicoCabecera;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public NivelEstudio getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(NivelEstudio nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getServicioEmergencia() {
        return servicioEmergencia;
    }

    public void setServicioEmergencia(String servicioEmergencia) {
        this.servicioEmergencia = servicioEmergencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoMedicoCabecera() {
        return telefonoMedicoCabecera;
    }

    public void setTelefonoMedicoCabecera(String telefonoMedicoCabecera) {
        this.telefonoMedicoCabecera = telefonoMedicoCabecera;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public boolean isPrivadoLibertad() {
        return privadoLibertad;
    }

    public void setPrivadoLibertad(boolean privadoLibertad) {
        this.privadoLibertad = privadoLibertad;
    }

    public String getPrivadoLibertadDonde() {
        return privadoLibertadDonde;
    }

    public void setPrivadoLibertadDonde(String privadoLibertadDonde) {
        this.privadoLibertadDonde = privadoLibertadDonde;
    }

    @Override
    public void darDeAlta() {
        estadoAlta = Bajeable.ALTA;
        fechaBaja = null;
        motivoBaja = null;
    }

    @Override
    public void darDeBaja(String motivo) throws SAPOValidationException {
        if (estadoAlta == Bajeable.ALTA && motivoBaja == null && fechaBaja == null) {
            estadoAlta = Bajeable.BAJA;
            this.motivoBaja = motivo;
            fechaBaja = FechaUtils.convertDateToCalendar(Calendar.getInstance().getTime());
        } else {
            HashMap<String, String> errors = new HashMap<String, String>(1);
            errors.put("Entidad actualmente dada de baja", "La entidad ya se encuentra dada de baja.");
            throw new SAPOValidationException(errors);
        }
    }

    @Override
    public void darDeBaja(String motivo, Calendar fechaDeBaja) throws SAPOValidationException {
        darDeBaja(motivo);
        fechaBaja = fechaDeBaja != null ? fechaDeBaja : this.fechaBaja;
    }

}
