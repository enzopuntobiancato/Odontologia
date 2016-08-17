package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "roles")
public class Rol extends EntityBase {
    private static final long serialVersionUID = 2562846295039018053L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEnum nombre;

    @OneToMany(mappedBy = "rol")
    private List<Privilegio> privilegios;

    @Size(max = 250, message = "La descripcion del rol no puede tener mas de 250 caracteres")
    @Column(length = 250)
    private String descripcion;

    public Rol() {
        privilegios = new ArrayList<Privilegio>();
    }

    public Rol(RolEnum nombre) {
        this.nombre = nombre;
    }

    public RolEnum getNombre() {
        return nombre;
    }

    public void setNombre(RolEnum nombre) {
        this.nombre = nombre;
    }

    public List<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean addPrivilegio(Privilegio p) {
        if(p == null || privilegios == null) {
            return false;
        }
        return this.privilegios.add(p);
    }

    public boolean removePrivilegio(Privilegio p) {
        if(p == null || privilegios == null) {
            return false;
        }
        return this.privilegios.remove(p);
    }

    public void clearPrivilegio() {
        if(privilegios == null) return;
        this.privilegios.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        if (!super.equals(o)) return false;

        Rol rol = (Rol) o;

        if (nombre != null ? !nombre.equals(rol.nombre) : rol.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
    }

}
