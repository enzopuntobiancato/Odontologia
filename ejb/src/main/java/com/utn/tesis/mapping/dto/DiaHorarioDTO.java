package com.utn.tesis.mapping.dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/02/16
 * Time: 18:28
 */
public class DiaHorarioDTO extends BaseDTO {
    private static final long serialVersionUID = 4830757538989653720L;

    private EnumDTO dia;
    private Calendar horaDesde;
    private Calendar horaHasta;

    public EnumDTO getDia() {
        return dia;
    }

    public void setDia(EnumDTO dia) {
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
}
