package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by cids on 23/06/2015.
 */
@Entity
public class Privilegio extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "funcionalidadId")
    private Funcionalidad funcionalidad;
    private boolean esItemMenu;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="rol_id", nullable=false)
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
}
