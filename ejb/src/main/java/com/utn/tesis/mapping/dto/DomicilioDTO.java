package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 20/02/16
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class DomicilioDTO extends BaseDTO{
    private BarrioDTO barrio;
    private String calle;
    private String numeroCalle;
    private int codigoPostal;
    private int piso;
    private String departamento;

    public BarrioDTO getBarrio() {
        return barrio;
    }

    public void setBarrio(BarrioDTO barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

}
