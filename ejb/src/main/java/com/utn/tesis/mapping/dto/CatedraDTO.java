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

}
