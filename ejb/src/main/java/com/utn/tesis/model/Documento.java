package com.utn.tesis.model;

import com.utn.tesis.annotation.JsonMap;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 19/05/15
 * Time: 23:39
 */
@Embeddable
public class Documento implements Serializable {

    @Size(max = 10, message = "El número de documento no puede ser mayor a 10 caracteres.")
    @NotNull(message = "Ingrese el número de documento.")
    @Column(nullable = false, length = 10)
    private String numero;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de documento no puede ser nulo.")
    private TipoDocumento tipoDocumento;

    public Documento() {
    }

    public Documento(String numero, TipoDocumento tipo) {
        this.numero = numero;
        this.tipoDocumento = tipo;
    }

    @JsonMap(view = JsonMap.Public.class)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @JsonMap(view = JsonMap.Public.class)
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public String toString() {
        return tipoDocumento.toString() + " " + numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Documento)) return false;

        Documento documento = (Documento) o;

        if (numero != null ? !numero.equals(documento.numero) : documento.numero != null) return false;
        if (tipoDocumento != documento.tipoDocumento) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numero != null ? numero.hashCode() : 0;
        result = 31 * result + (tipoDocumento != null ? tipoDocumento.hashCode() : 0);
        return result;
    }
}
