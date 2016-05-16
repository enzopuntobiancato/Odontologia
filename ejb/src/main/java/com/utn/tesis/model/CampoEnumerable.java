/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampoEnumerable)) return false;
        if (!super.equals(o)) return false;

        CampoEnumerable that = (CampoEnumerable) o;

        if (checked != null ? !checked.equals(that.checked) : that.checked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        return result;
    }
}
