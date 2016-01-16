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
@DiscriminatorValue(value = "CAMPOENUMERABLE")
public class CampoEnumerable extends DetalleHistoriaClinica {

    private Boolean checked;

    public CampoEnumerable() {
        checked = false;
    }

    public CampoEnumerable(String nombre, Integer grupo, Integer pregunta) {
        super(nombre, grupo, pregunta);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}
