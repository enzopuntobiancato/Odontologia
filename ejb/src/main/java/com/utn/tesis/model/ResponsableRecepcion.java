package com.utn.tesis.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsables_recepcion")
public class ResponsableRecepcion extends Persona {
    private static final long serialVersionUID = -7743898999144645510L;

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
