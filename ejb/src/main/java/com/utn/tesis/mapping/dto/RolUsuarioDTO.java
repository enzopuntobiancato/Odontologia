package com.utn.tesis.mapping.dto;

public class RolUsuarioDTO extends BaseDTO {
    private static final long serialVersionUID = 4456141852497497550L;

    private RolDTO rol;
    private Long personaId;
    private PersonaDTO persona;

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }
}
