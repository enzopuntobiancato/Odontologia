package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class PracticaOdontologica extends EntityBase {

    private String denominacion;
    private String codigo;
    private String observaciones;
    private GrupoPracticaOdontologica grupo;

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public GrupoPracticaOdontologica getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoPracticaOdontologica grupo) {
        this.grupo = grupo;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
