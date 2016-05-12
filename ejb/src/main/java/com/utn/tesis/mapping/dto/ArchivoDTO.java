package com.utn.tesis.mapping.dto;

import com.utn.tesis.model.FileExtension;

import java.io.InputStream;

public class ArchivoDTO extends BaseDTO {
    private static final long serialVersionUID = 1907406119567098502L;

    private Long id;
    private String nombre;
    private String descripcion;
    private InputStream archivo;
    private FileExtension extension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public InputStream getArchivo() {
        return archivo;
    }

    public void setArchivo(InputStream archivo) {
        this.archivo = archivo;
    }

    public FileExtension getExtension() {
        return extension;
    }

    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }
}
