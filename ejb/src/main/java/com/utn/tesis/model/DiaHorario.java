package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Calendar;


@Entity
public class DiaHorario extends EntityBase {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Debe seleccionar un día de la semana.")
    @Enumerated(EnumType.STRING)
    private Dia dia;

    @NotNull(message = "Debe ingresar hora desde.")
    private Calendar horaDesde;

    @NotNull(message = "Debe ingresar hora hasta.")
    private Calendar horaHasta;

    public DiaHorario() {
    }

    public DiaHorario(Dia dia, Calendar horaDesde, Calendar horaHasta) {
        this.dia = dia;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Calendar getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(Calendar horaDesde) {
        this.horaDesde = horaDesde;
    }

    public Calendar getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(Calendar horaHasta) {
        this.horaHasta = horaHasta;
    }


    @Override
    public void validar() throws SAPOValidationException {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiaHorario)) return false;
        if (!super.equals(o)) return false;

        DiaHorario that = (DiaHorario) o;

        if (dia != that.dia) return false;
        if (!horaDesde.equals(that.horaDesde)) return false;
        if (!horaHasta.equals(that.horaHasta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dia.hashCode();
        result = 31 * result + horaDesde.hashCode();
        result = 31 * result + horaHasta.hashCode();
        return result;
    }
}
