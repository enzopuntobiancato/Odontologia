package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 06/01/16
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Domicilio extends EntityBase {

    @NotNull
    @ManyToOne
    private Barrio barrio;

    @NotNull
    @Size(max = 100, message = "La calle no puede ser mayor a 100 caracteres")
    private String calle;

    private int codigoPostal;

    private String departamento;

    @NotNull
    @Size(max = 50, message = "El numero no puede ser mayor a 50 caracteres")
    private String numero;

    private int piso;

    public Domicilio() {
    }

    public Domicilio(Barrio barrio, String calle, String numero) {
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
