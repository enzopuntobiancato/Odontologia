package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "grupos_funcionalidad")
public class GrupoFuncionalidad extends EntityBase {
    private static final long serialVersionUID = 4313700820205637663L;

    @NotNull
    @Column(nullable = false, length = 255)
    private String nombre;

    public GrupoFuncionalidad() {
    }

    public GrupoFuncionalidad(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void validar() throws SAPOValidationException {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
