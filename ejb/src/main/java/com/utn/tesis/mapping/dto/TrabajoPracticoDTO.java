package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/02/16
 * Time: 18:46
 */
public class TrabajoPracticoDTO extends BaseDTO {
    private static final long serialVersionUID = 4315782049030498725L;

    private String nombre;
    private PracticaOdontologicaDTO practicaOdontologica;
    private String descripcion;
    private String motivoBaja;
    private String fechaBaja;

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PracticaOdontologicaDTO getPracticaOdontologica() {
        return practicaOdontologica;
    }

    public void setPracticaOdontologica(PracticaOdontologicaDTO practicaOdontologica) {
        this.practicaOdontologica = practicaOdontologica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
