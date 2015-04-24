package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
public class Catedra extends EntityBase {
    private static final long serialVersionUID = 1L;

    @NotNull (message = "Debe ingresar una denominación.")
    private String denominacion;

    @Size (max = 400, message = "La descripcion debe tener entre 0 y 400 caracteres.")
    private String descripcion;

    @NotNull (message = "Debe ingresar días y horarios.")
    @OneToMany(targetEntity = DiaHorario.class, mappedBy = "catedra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiaHorario> horarios;

    @ManyToOne
    @JoinColumn(name = "materiaId")
    @NotNull(message = "La catedra debe pertenecer a una materia.")
    private Materia materia;

    public Catedra() {
        horarios = new ArrayList<DiaHorario>();
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DiaHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DiaHorario> horarios) {
        this.horarios = horarios;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();
        if(horarios == null)
           e.put("Null", "La lista de horarios no puede ser nula.");
        if(horarios.isEmpty())
            e.put("Empty", "La lista de horarios de la catedra no puede estar vacia.");
        if(!e.isEmpty())
            throw new SAPOValidationException(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catedra)) return false;
        if (!super.equals(o)) return false;

        Catedra catedra = (Catedra) o;

        if (!denominacion.equals(catedra.denominacion)) return false;
        if (!horarios.equals(catedra.horarios)) return false;
        if (!materia.equals(catedra.materia)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + denominacion.hashCode();
        result = 31 * result + horarios.hashCode();
        result = 31 * result + materia.hashCode();
        return result;
    }
}
