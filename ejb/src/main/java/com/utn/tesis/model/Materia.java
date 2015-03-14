package com.utn.tesis.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 10/02/15
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "MATERIA")
public class Materia extends EntityBase {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Debe ingresar un nombre")
    @Size(max = 50)
    private String nombre;

    @NotNull(message = "Debe ingresar el nivel")
    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Size(max = 400)
    private String descripcion;

//    @OneToMany(targetEntity = Catedra.class, mappedBy = "materia")
//    private List<Catedra> catedras;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public List<Catedra> getCatedras() {
//        return catedras;
//    }
//
//    public void setCatedras(List<Catedra> catedras) {
//        this.catedras = catedras;
//    }
}
