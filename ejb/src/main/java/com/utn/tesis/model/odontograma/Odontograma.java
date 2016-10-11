package com.utn.tesis.model.odontograma;

import java.util.Calendar;
import java.util.List;

public class Odontograma {

    private boolean esAdulto;
    private Calendar fecha;
    private List<PiezaDental> piezasDentales;

    public boolean isEsAdulto() {
        return esAdulto;
    }

    public void setEsAdulto(boolean esAdulto) {
        this.esAdulto = esAdulto;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public List<PiezaDental> getPiezasDentales() {
        return piezasDentales;
    }

    public void setPiezasDentales(List<PiezaDental> piezasDentales) {
        this.piezasDentales = piezasDentales;
    }
}
