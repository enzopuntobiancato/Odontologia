/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CAMPOSINO")
public class CampoSiNo extends DetalleHistoriaClinica {

    private Boolean siNo;

    public CampoSiNo() {
        siNo = false;
    }

    public CampoSiNo(String nombre, Integer grupo, Integer pregunta) {
        super(nombre, grupo, pregunta);
    }

    public Boolean getSiNo() {
        return siNo;
    }

    public void setSiNo(Boolean siNo) {
        this.siNo = siNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampoSiNo)) return false;
        if (!super.equals(o)) return false;

        CampoSiNo campoSiNo = (CampoSiNo) o;

        if (siNo != null ? !siNo.equals(campoSiNo.siNo) : campoSiNo.siNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (siNo != null ? siNo.hashCode() : 0);
        return result;
    }
}
