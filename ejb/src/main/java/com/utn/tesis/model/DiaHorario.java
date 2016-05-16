package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name = "dias_horarios")
public class DiaHorario extends EntityBase {
    private static final long serialVersionUID = 1368287238397612789L;

    @NotNull(message = "Debe seleccionar un día de la semana.")
    @Enumerated(EnumType.STRING)
    private Dia diaSemana;

    @NotNull(message = "Debe ingresar hora desde.")
    @Column(name = "hora_desde")
    private Calendar horaDesde;

    @NotNull(message = "Debe ingresar hora hasta.")
    @Column(name = "hora_hasta")
    private Calendar horaHasta;

    @ManyToOne
    @JoinColumn(name = "catedra_id")
    private Catedra catedra;

    public DiaHorario() {
    }

    public DiaHorario(Dia diaSemana, Calendar horaDesde, Calendar horaHasta) {
        this.diaSemana = diaSemana;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
    }

    public Dia getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Dia diaSemana) {
        this.diaSemana = diaSemana;
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

    public Catedra getCatedra() {
        return catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
        if(catedra != null && !catedra.getHorarios().contains(this))
            catedra.getHorarios().add(this);
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

        if (diaSemana != that.diaSemana) return false;
        if (!horaDesde.equals(that.horaDesde)) return false;
        if (!horaHasta.equals(that.horaHasta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + diaSemana.hashCode();
        result = 31 * result + horaDesde.hashCode();
        result = 31 * result + horaHasta.hashCode();
        return result;
    }
}
