package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
public class Catedra extends Bajeable {
    private static final long serialVersionUID = 1L;

    @NotNull (message = "Debe ingresar una denominación.")
    @Size (max = 10, message = "La denominacion no puede ser mayor a 10 caracteres.")
    private String denominacion;

    @Size (max = 400, message = "La descripcion debe tener entre 0 y 400 caracteres.")
    private String descripcion;

    @JsonManagedReference
    @NotNull (message = "Debe ingresar días y horarios.")
    @OneToMany(targetEntity = DiaHorario.class, mappedBy = "catedra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiaHorario> horarios;

    @ManyToOne
    @JoinColumn(name = "materiaId")
    @NotNull(message = "La catedra debe pertenecer a una materia.")
    private Materia materia;

    @ManyToMany(mappedBy = "catedras")
    private List<Profesor> profesores;

    @ManyToMany
    @JoinTable(name = "catedra_x_trabajo_practico",
            joinColumns = {
                    @JoinColumn(name = "catedra_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "trabajo_practico_id")})
    private List<TrabajoPractico> trabajosPracticos;

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

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public List<TrabajoPractico> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    public void setTrabajosPracticos(List<TrabajoPractico> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
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

        if (denominacion != null ? !denominacion.equals(catedra.denominacion) : catedra.denominacion != null)
            return false;
        if (descripcion != null ? !descripcion.equals(catedra.descripcion) : catedra.descripcion != null) return false;
        if (horarios != null ? !horarios.equals(catedra.horarios) : catedra.horarios != null) return false;
        if (materia != null ? !materia.equals(catedra.materia) : catedra.materia != null) return false;
        if (profesores != null ? !profesores.equals(catedra.profesores) : catedra.profesores != null) return false;
        if (trabajosPracticos != null ? !trabajosPracticos.equals(catedra.trabajosPracticos) : catedra.trabajosPracticos != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (denominacion != null ? denominacion.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (materia != null ? materia.hashCode() : 0);
        result = 31 * result + (profesores != null ? profesores.hashCode() : 0);
        result = 31 * result + (trabajosPracticos != null ? trabajosPracticos.hashCode() : 0);
        return result;
    }
}
