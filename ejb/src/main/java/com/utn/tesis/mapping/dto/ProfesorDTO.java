package com.utn.tesis.mapping.dto;


import java.util.List;

public class ProfesorDTO extends PersonaDTO {
    private static final long serialVersionUID = -2624365127909522877L;

    private Integer legajo;
    private String matricula;
    private String profesion;
    private List<CatedraDTO> catedras;

    //GETTERS Y SETTERS
    public List<CatedraDTO> getCatedras() {
        return catedras;
    }

    public void setCatedras(List<CatedraDTO> catedras) {
        this.catedras = catedras;
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
