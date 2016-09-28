package com.utn.tesis.mapping.dto;


public class ProfesorDTO extends PersonaDTO {
    private static final long serialVersionUID = -2624365127909522877L;

    private Integer legajo;
    private String matricula;
    private String profesion;

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
