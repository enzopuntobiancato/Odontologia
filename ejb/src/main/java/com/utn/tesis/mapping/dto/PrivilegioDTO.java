package com.utn.tesis.mapping.dto;

public class PrivilegioDTO extends BaseDTO {
    private static final long serialVersionUID = -4051279900072584076L;

    private FuncionalidadDTO funcionalidad;
    private boolean esItemMenu;

    public FuncionalidadDTO getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(FuncionalidadDTO funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public boolean isEsItemMenu() {
        return esItemMenu;
    }

    public void setEsItemMenu(boolean esItemMenu) {
        this.esItemMenu = esItemMenu;
    }
}
