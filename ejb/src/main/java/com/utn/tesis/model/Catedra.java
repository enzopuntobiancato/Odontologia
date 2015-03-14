package com.utn.tesis.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class Catedra extends EntityBase {
    private static final long serialVersionUID = 1L;

    @NotNull (message = "Debe ingresar una denominación")
    private String denominacion;

    @Size (max = 400)
    private String descripcion;

    @NotNull (message = "Debe ingresar días y horarios")
    @OneToMany(targetEntity = DiaHorario.class, mappedBy = "catedra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DiaHorario> horarios;

    @ManyToOne
    @JoinColumn(name = "materiaId")
    private Materia materia;


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
}
