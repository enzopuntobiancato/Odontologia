package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "domicilios")
public class Domicilio extends EntityBase {
    private static final long serialVersionUID = 118449969034704193L;

    @NotNull(message = "El barrio no puede ser nulo.")
    @ManyToOne
    private Barrio barrio;

    @NotNull(message = "El nombre de la calle no puede ser nulo.")
    @Size(max = 100, message = "La calle no puede ser mayor a 100 caracteres")
    @Column(nullable = false, length = 100)
    private String calle;

    @Column(name = "codigo_postal")
    private Integer codigoPostal;

    @Size(max = 50, message = "El departamento no puede ser mayor a 50 caracteres.")
    @Column(length = 50)
    private String departamento;

    @NotNull(message = "La numeracion de la calle no puede ser nula.")
    @Size(max = 50, message = "El numero no puede ser mayor a 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String numero;

    private Integer piso;

    public Domicilio() {
    }

    public Domicilio(Barrio barrio, String calle, String numero) {
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domicilio)) return false;
        if (!super.equals(o)) return false;

        Domicilio domicilio = (Domicilio) o;

        if (codigoPostal != domicilio.codigoPostal) return false;
        if (piso != domicilio.piso) return false;
        if (barrio != null ? !barrio.equals(domicilio.barrio) : domicilio.barrio != null) return false;
        if (calle != null ? !calle.equals(domicilio.calle) : domicilio.calle != null) return false;
        if (departamento != null ? !departamento.equals(domicilio.departamento) : domicilio.departamento != null)
            return false;
        if (numero != null ? !numero.equals(domicilio.numero) : domicilio.numero != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (barrio != null ? barrio.hashCode() : 0);
        result = 31 * result + (calle != null ? calle.hashCode() : 0);
        result = 31 * result + (codigoPostal!= null ? codigoPostal: 0);
        result = 31 * result + (departamento != null ? departamento.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        result = 31 * result + (piso != null ? piso : 0);
        return result;
    }
}
