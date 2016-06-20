package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/05/16
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class CampoSiNoDTO extends DetalleHistoriaClinicaDTO {
    private Boolean siNo;

    //CONSTRUCTOR

    public CampoSiNoDTO() {
    }

    //GETTERS AND SETTERS
    public Boolean getSiNo() {
        return siNo;
    }

    public void setSiNo(Boolean siNo) {
        this.siNo = siNo;
    }
}
