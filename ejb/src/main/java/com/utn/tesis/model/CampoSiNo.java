/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Maxi
 */
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

}
