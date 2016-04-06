package com.utn.tesis.mapping.dto;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/02/16
 * Time: 18:19
 */
public class CatedraDTO extends BaseDTO {
    private static final long serialVersionUID = -4824818177254887378L;

    private Long id;
    private Calendar fechaBaja;
    private String motivoBaja;
    private String denominacion;
    private String descripcion;
    private List<DiaHorarioDTO> horarios;
    private MateriaDTO materia;
    private List<TrabajoPracticoDTO> trabajosPracticos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Calendar fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DiaHorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DiaHorarioDTO> horarios) {
        this.horarios = horarios;
    }

    public MateriaDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriaDTO materia) {
        this.materia = materia;
    }

    public List<TrabajoPracticoDTO> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    public void setTrabajosPracticos(List<TrabajoPracticoDTO> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
    }
}
