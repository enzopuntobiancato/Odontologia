package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TABLA DEFINIDA. NO HAY ABM. SE MANTIENE POR DB.
@Entity
@Table(name = "grupos_practica_odontologica")
public class GrupoPracticaOdontologica extends EntityBase {
    private static final long serialVersionUID = -2936660790337725419L;

    @NotNull(message = "El nombre del grupo de practica odontologica no puede ser nulo.")
    @Size(max = 50, message = "El nombre del grupo de la practica odontologica debe tener entre 0 y 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nombre;

    public GrupoPracticaOdontologica() {
    }

    public GrupoPracticaOdontologica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoPracticaOdontologica)) return false;
        if (!super.equals(o)) return false;

        GrupoPracticaOdontologica that = (GrupoPracticaOdontologica) o;

        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
