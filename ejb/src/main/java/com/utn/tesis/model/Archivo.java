package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Entity
@Table(name = "archivos")
public class Archivo extends EntityBase {
    private static final long serialVersionUID = -1564724278808557651L;

    @NotNull(message = "Debe ingresar un nombre del archivo.")
    @Size(max = 50, message = "El nombre debe tener entre 0 y 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotNull(message = "Debe ingresar una ruta para el archivo.")
    @Size(max = 150, message = "El nombre debe tener entre 0 y 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String ruta;

    @Size(max = 150, message = "La descripcion debe tener entre 0 y 150 caracteres.")
    @Column(length = 150)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
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

        if (nombre == null) {
            e.put("Nombre null", "El nombre del archivo no puede ser nulo.");
        }

        if (ruta.isEmpty()) {
            e.put("Ruta null", "La ruta del archivo no puede ser nula.");
        }

        if (extension == null) {
            e.put("Extension null", "La extension del archivo no puede ser nula.");
        }

        if (!e.isEmpty()) {
            throw new SAPOValidationException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Archivo)) return false;
        if (!super.equals(o)) return false;

        Archivo archivo = (Archivo) o;

        if (extension != archivo.extension) return false;
        if (nombre != null ? !nombre.equals(archivo.nombre) : archivo.nombre != null) return false;
        if (ruta != null ? !ruta.equals(archivo.ruta) : archivo.ruta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (ruta != null ? ruta.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }
}

