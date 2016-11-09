package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "privilegios")
public class Privilegio extends EntityBase {
    private static final long serialVersionUID = 7366318752748840729L;

    @ManyToOne
    @JoinColumn(name = "funcionalidad_id")
    private Funcionalidad funcionalidad;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    @NotNull(message = "El rol no puede ser nulo.")
    private Rol rol;

    public Privilegio() {
    }

    public Privilegio(Funcionalidad funcionalidad, Rol rol) {
        this.funcionalidad = funcionalidad;
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
