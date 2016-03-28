package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by cids on 23/06/2015.
 */
@Entity
public class Privilegio extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "funcionalidadId")
    private Funcionalidad funcionalidad;
    private boolean esItemMenu;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Privilegio() {
    }

    public Privilegio(Funcionalidad funcionalidad, boolean esItemMenu, Rol rol) {
        this.funcionalidad = funcionalidad;
        this.esItemMenu = esItemMenu;
        this.rol = rol;
    }

    @Override
    public void validar() throws SAPOValidationException {

    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public boolean isEsItemMenu() {
        return esItemMenu;
    }

    public void setEsItemMenu(boolean esItemMenu) {
        this.esItemMenu = esItemMenu;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilegio)) return false;
        if (!super.equals(o)) return false;

        Privilegio that = (Privilegio) o;

        if (funcionalidad != null ? !funcionalidad.equals(that.funcionalidad) : that.funcionalidad != null)
            return false;
        if (rol != null ? !rol.equals(that.rol) : that.rol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (funcionalidad != null ? funcionalidad.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        return result;
    }
}
