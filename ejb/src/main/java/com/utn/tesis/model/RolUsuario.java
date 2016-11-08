package com.utn.tesis.model;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.service.RolService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles_x_usuario")
public class RolUsuario extends EntityBase {
    private static final long serialVersionUID = 7763003995862089924L;

    @ManyToOne
    @NotNull(message = "El rol no puede ser nulo")
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
    @ManyToOne
    @NotNull(message = "La persona no puede ser nula")
    @JoinColumn(nullable = false, name = "persona_id")
    private Persona persona;

    @Override
    public void validar() throws SAPOValidationException {
        if (!RolService.rolToPerson.get(rol.getNombre().getKey()).equals(persona.getClass())) {
            throw new SAPOValidationException(ImmutableMap.of("persona", "El tipo de persona no coincide con el rol asociado."));
        }
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
