package com.utn.tesis.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 11/01/16
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class ResponsableRecepcion extends Persona {

    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiaHorario> horarios;

    public ResponsableRecepcion() {
        this.horarios = new ArrayList<DiaHorario>();
    }

    public List<DiaHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DiaHorario> horarios) {
        this.horarios = horarios;
    }

    public boolean addHorario(DiaHorario dh) {
        if (horarios == null) return false;
        return horarios.add(dh);
    }

    public boolean removeHorario(DiaHorario dh) {
        if (horarios == null) return false;
        return horarios.remove(dh);
    }

}
