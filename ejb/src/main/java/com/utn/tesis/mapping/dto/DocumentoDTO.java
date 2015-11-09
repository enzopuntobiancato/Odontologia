package com.utn.tesis.mapping.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:36
 */
public class DocumentoDTO extends BaseDTO {
    private static final long serialVersionUID = 7564164866055416914L;

    private String numero;
    private EnumDTO tipoDocumento;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public EnumDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(EnumDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
