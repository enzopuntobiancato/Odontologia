package com.utn.tesis.model.odontograma;

import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@JsonTypeInfo(use = Id.CLASS,
        include = As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = Puente.class),
        @Type(value = Caries.class, name = "caries"),
        @Type(value = Extraccion.class),
        @Type(value = Corona.class),
        @Type(value = Sellador.class),
        @Type(value = ProtesisCompleta.class),
        @Type(value = ProtesisParcial.class),
        @Type(value = TratamientoDeConducto.class),
})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class HallazgoClinico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private EstadoHallazgoClinico estado;
//    private Integer diagnosticoId;

    //CONSTRUCTOR
    public HallazgoClinico() {
    }

    protected HallazgoClinico(EstadoHallazgoClinico estado) {
        setEstado(estado);
        this.nombre = getNombre();
    }

    //GETTERS Y SETTERS
    /*public Integer getDiagnosticoId() {
        return diagnosticoId;
    }

    public void setDiagnosticoId(Integer diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
    }*/

    public abstract String getNombre();


    public EstadoHallazgoClinico getEstado() {
        return estado;
    }

    public void setEstado(EstadoHallazgoClinico estado) {
        this.estado = estado;
    }

    //METODOS AUXILIARES
    public boolean isAplicaACara() {
        return false;
    }

    public boolean isAplicaACaraCentral() {
        return false;
    }

    public boolean isAplicaAPieza() {
        return false;
    }

    public boolean isAplicaAPiezaGrupal() {
        return false;
    }

    public boolean isAplicaAPosicion() {
        return false;
    }

    public List<Integer> getPosiciones() {
        return Collections.emptyList();
    }

    /**
     * Definimos el ID del "dibujo/marca" correspondiente del hallazgo
     * @return
     */
    public abstract String getMarkID();
}
