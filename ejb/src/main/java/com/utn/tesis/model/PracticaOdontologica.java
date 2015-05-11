package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PracticaOdontologica extends Bajeable {


    @NotNull(message = "El nombre de la práctica no puede ser nulo.")
    @Size(max = 75, message = "El nombre de la práctica debe tener entre 0 y 75 caracteres.")
    private String denominacion;

    @Size(max = 200, message = "La observación de la práctica debe tener entre 0 y 200 caracteres.")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "grupoId")
    @NotNull(message = "El grupo al cual pertenece la práctica no puede ser nulo.")
    private GrupoPracticaOdontologica grupo;

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PracticaOdontologica)) return false;
        if (!super.equals(o)) return false;

        PracticaOdontologica that = (PracticaOdontologica) o;

        if (denominacion != null ? !denominacion.equals(that.denominacion) : that.denominacion != null) return false;
        if (grupo != null ? !grupo.equals(that.grupo) : that.grupo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (denominacion != null ? denominacion.hashCode() : 0);
        result = 31 * result + (grupo != null ? grupo.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
