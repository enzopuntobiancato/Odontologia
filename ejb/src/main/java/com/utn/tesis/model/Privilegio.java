package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by cids on 23/06/2015.
 */
public class Privilegio extends EntityBase {

    private Funcionalidad funcionalidad;
    private boolean esItemMenu;
    @ManyToMany(mappedBy = "privilegios")
    private List<Rol> roles;

    public Privilegio() {
    }

    public Privilegio(Funcionalidad funcionalidad, boolean esItemMenu, List<Rol> roles) {
        this.funcionalidad = funcionalidad;
        this.esItemMenu = esItemMenu;
        this.roles = roles;
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

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
