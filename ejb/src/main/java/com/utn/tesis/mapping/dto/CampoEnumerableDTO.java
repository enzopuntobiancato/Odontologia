package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/05/16
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class CampoEnumerableDTO extends DetalleHistoriaClinicaDTO {
    private Boolean checked;

    //GETTERS AND SETTERS
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
