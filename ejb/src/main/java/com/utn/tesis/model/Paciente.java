package com.utn.tesis.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 13/01/16
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Paciente extends Persona {

    @Size(max = 30, message = "El celular no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String celular;

    @Size(max = 50, message = "El email no puede tener mas de 50 caracteres.")
    @Column(length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Archivo imagen;

    @ManyToOne
    private Ciudad lugarDeNacimiento;

    @Size(max = 50, message = "El nombre del medico de cabecera no puede tener mas de 50 caracteres.")
    @Column(length = 50)
    private String medicoCabecera;

    @Enumerated(EnumType.STRING)
    private Nacionalidad nacionalidad;

    @Enumerated(EnumType.STRING)
    private NivelEstudio nivelEstudio;

    @ManyToOne
    private ObraSocial obraSocial;

    @Size(max = 30, message = "El campo religion no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String religion;

    @Size(max = 30, message = "El servicio de emergencia no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String servicioEmergencia;

    @Size(max = 30, message = "El telefono no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String telefono;

    @Size(max = 30, message = "El telefono del medico de cabecera no puede tener mas de 30 caracteres.")
    @Column(length = 30)
    private String telefonoMedicoCabecera;

    @ManyToOne
    private Trabajo trabajo;

    @ManyToOne
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
}
