package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 11/05/15
 * Time: 22:08
 */
@Entity
public class TrabajoPractico extends Bajeable {
    private static final long serialVersionUID = -6891546733104546655L;

    @NotNull(message = "Debe ingresar un nombre.")
    @Size(max = 100, message = "El nombre no puede ser mayor a 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "Debe ingresar una descripción.")
    @Size(max = 400, message = "La descripción no puede ser mayor a 400 caracteres.")
    @Column(nullable = false, length = 400)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "practicaOdontologicaId")
    @NotNull(message = "Debe seleccionar una práctica odontológica.")
    private PracticaOdontologica practicaOdontologica;

    @ManyToMany(mappedBy = "trabajosPracticos")
    private List<Catedra> catedras;

    @Override
    public void validar() throws SAPOValidationException {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public PracticaOdontologica getPracticaOdontologica() {
        return practicaOdontologica;
    }

    public void setPracticaOdontologica(PracticaOdontologica practicaOdontologica) {
        this.practicaOdontologica = practicaOdontologica;
    }

    public List<Catedra> getCatedras() {
        return catedras;
    }

    public void setCatedras(List<Catedra> catedras) {
        this.catedras = catedras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrabajoPractico)) return false;
        if (!super.equals(o)) return false;

        TrabajoPractico that = (TrabajoPractico) o;

        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (practicaOdontologica != null ? !practicaOdontologica.equals(that.practicaOdontologica) : that.practicaOdontologica != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (practicaOdontologica != null ? practicaOdontologica.hashCode() : 0);
        return result;
    }
}
