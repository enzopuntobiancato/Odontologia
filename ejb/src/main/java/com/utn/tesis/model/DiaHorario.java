package com.utn.tesis.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class DiaHorario extends EntityBase {
    private static final long serialVersionUID = 1L;

    @NotNull (message = "Debe seleccionar un dia de la semana")
    @Enumerated(EnumType.STRING)
    private Dia dia;

    @NotNull (message = "Debe ingresar hora desde")
    private String horaDesde;

    @NotNull (message = "Debe ingresar hora hasta")
    private String horaHasta;

    @ManyToOne
    @JoinColumn (name = "catedraId")
    private Catedra catedra;

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public Catedra getCatedra() {
        return catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
    }
}
