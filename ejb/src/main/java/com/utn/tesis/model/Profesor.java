package com.utn.tesis.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 20/05/15
 * Time: 00:05
 */
@Entity
public class Profesor extends Persona {

    @ManyToMany
    @JoinTable(name = "profesor_x_catedra",
            joinColumns = {
                    @JoinColumn(name = "profesor_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "catedra_id")})
     private List<Catedra> catedras;

    public Profesor() {
        this.catedras = new ArrayList<Catedra>();
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
        if (!(o instanceof Profesor)) return false;
        if (!super.equals(o)) return false;

        Profesor profesor = (Profesor) o;

        if (catedras != null ? !catedras.equals(profesor.catedras) : profesor.catedras != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (catedras != null ? catedras.hashCode() : 0);
        return result;
    }
}
