package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonBackReference;

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

    @JsonBackReference
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

    @Override
    public void validar() throws SAPOValidationException {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiaHorario)) return false;
        if (!super.equals(o)) return false;

        DiaHorario that = (DiaHorario) o;

        if (!catedra.equals(that.catedra)) return false;
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
        result = 31 * result + catedra.hashCode();
        return result;
    }
}
