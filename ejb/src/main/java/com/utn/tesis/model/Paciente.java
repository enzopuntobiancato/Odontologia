package com.utn.tesis.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pacientes")
public class Paciente extends Persona {
    private static final long serialVersionUID = -836105309387103995L;

    @Size(max = 30, message = "El celular no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String celular;

    @Size(max = 50, message = "El email no puede tener mas de 50 caracteres.")
    @Column(length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "archivo_imagen_id")
    private Archivo imagen;

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

    @ManyToOne
    @JoinColumn(name = "trabajo_id")
    private Trabajo trabajo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    @ManyToOne
    @JoinColumn(name = "historia_clinica_id")
    private HistoriaClinica historiaClinica;

    public Paciente() {
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

}
