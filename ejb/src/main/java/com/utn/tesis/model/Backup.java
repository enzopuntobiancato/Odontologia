package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "backups")
public class Backup extends EntityBase {
    private static final long serialVersionUID = -7567547572629993553L;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_generacion")
    private Calendar fechaGeneracion;
    private String nombre;
    private String path;

    public Calendar getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Calendar fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
