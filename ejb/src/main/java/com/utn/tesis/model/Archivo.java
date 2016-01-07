package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.exception.SAPOValidationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 03/06/15
 * Time: 21:58
 */
@Entity
public class Archivo extends EntityBase {

    @NotNull(message = "Debe ingresar un nombre del archivo.")
    @Size(max = 50, message = "El nombre debe tener entre 0 y 50 caracteres.")
    private String nombre;

    @NotNull(message = "Debe ingresar una ruta para el archivo.")
    @Size(max = 150, message = "El nombre debe tener entre 0 y 150 caracteres.")
    private String ruta;

    @Size(max = 150, message = "La descripcion debe tener entre 0 y 150 caracteres.")
    private String descripcion;

    @NotNull
    private FileExtension extension;

    public Archivo() {
    }

    public Archivo(String nombre, String ruta, FileExtension extension) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public FileExtension getExtension() {
        return extension;
    }

    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }

    @Override
    public void validar() throws SAPOValidationException {
        HashMap<String, String> e = new HashMap<String, String>();

        if(nombre == null) {
            e.put("Nombre null", "El nombre del archivo no puede ser nulo.");
        }

        if(ruta.isEmpty()) {
            e.put("Ruta null", "La ruta del archivo no puede ser nula.");
        }

        if(extension == null) {
            e.put("Extension null", "La extension del archivo no puede ser nula.");
        }

        if(!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

}

