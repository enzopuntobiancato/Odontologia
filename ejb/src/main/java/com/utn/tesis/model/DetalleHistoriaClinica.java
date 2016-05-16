/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name = "detalle_historia_clinica")
public abstract class DetalleHistoriaClinica extends EntityBase {

    @Column(name = "nombre", length = 255)
    @Size(min = 1, max = 255)
    @NotNull
    private String nombre;

    @NotNull
    private Integer grupo;

    @NotNull
    private Integer pregunta;

    //PREGUNTAS GENERALES

    public static final String CUAL = "¿Cuál?";
    public static final String CUALES = "¿Cuáles?";
    public static final String CUANDO = "¿Cuando?";
    public static final String QUE = "¿Que?";
    public static final String DESDE_CUANDO = "¿Desde cuándo?";
    public static final String DE_QUE = "¿De que?";
    public static final String FECHA = "Fecha";
    public static final String TRATAMIENTO = "Tratamiento";
    public static final String PORQUE = "¿Por qué?";
    public static final String DONDE = "¿Donde?";
    public static final String CANTIDAD_DIARIA = "Cantidad diaria";
    public static final String OTROS = "Otros";


    //PREGUNTAS
    public static final String G1P1 = "¿Tiene algun problema cardiaco?";
    public static final String G1P2 = CUAL;
    public static final String G1P3 = DESDE_CUANDO;

    public static final String G2P1 = "¿Padece o padecio el mal de Chagas?";
    public static final String G2P2 = "¿Recibio tratamiento?";
    public static final String G2P3 = CUAL;
    public static final String G2P4 = DESDE_CUANDO;

    public static final String G3P1 = "¿Es hipertenso?";
    public static final String G3P2 = "¿Toma medicacion?";
    public static final String G3P3 = CUAL;
    public static final String G3P4 = DESDE_CUANDO;
    public static final String G3P5 = "Dosis diaria";
    public static final String G3P6 = "¿Le tomaron la P.S.A?";
    public static final String G3P7 = "¿Recuerda sus valores?";
    public static final String G3P8 = "Mx";
    public static final String G3P9 = "Mn";
    public static final String G3P10 = FECHA;
    public static final String G3P11 = "¿Durante el dia se le hinchan los pies?";

    public static final String G4P1 = "¿Padece o padecio fiebre reumatica?";
    public static final String G4P2 = "¿Recibio tratamiento?";
    public static final String G4P3 = CUAL;
    public static final String G4P4 = DESDE_CUANDO;

    public static final String G5P1 = "¿Tiene problemas en los riñones?";
    public static final String G5P2 = CUAL;
    public static final String G5P3 = DESDE_CUANDO;
    public static final String G5P4 = "¿Cuando se levanta tiene la cara hinchada?";
    public static final String G5P5 = "¿Toma algun medicamento?";
    public static final String G5P6 = CUAL;
    public static final String G5P7 = DESDE_CUANDO;
    public static final String G5P8 = "¿Se dializa?";
    public static final String G5P9 = DESDE_CUANDO;

    public static final String G6P1 = "¿Se resfria con facilidad?";
    public static final String G6P2 = "¿Tos?";
    public static final String G6P3 = "¿Se acompaña con secrecion?";
    public static final String G6P4 = "Tipo de secrecion";

    public static final String G7P1 = "¿Es asmatico?";
    public static final String G7P2 = "¿Requirio internacion?";

    public static final String G8P1 = "¿Es alergico?";
    public static final String G8P2 = "Anestesicos";
    public static final String G8P3 = "Antibioticos";
    public static final String G8P4 = "A.I.N.E.S.";
    public static final String G8P5 = "Yodo";
    public static final String G8P6 = OTROS;

    public static final String G9P1 = "¿Fue intervenido quirurgicamente?";
    public static final String G9P2 = DE_QUE;
    public static final String G9P3 = "¿Con que tipo de anestesia?";
    public static final String G9P4 = CUANDO;

    public static final String G10P1 = "¿Tuvo hepatitis?";
    public static final String G10P2 = "¿Que tipo?";
    public static final String G10P3 = "¿Hace cuanto tiempo?";
    public static final String G10P4 = "¿Se hace controles?";
    public static final String G10P5 = "¿Tiene problemas al tragar?";
    public static final String G10P6 = "¿Tiene dolores de estomago?";
    public static final String G10P7 = "¿Antes o despues de comer?";
    public static final String G10P8 = "¿Tiene ulceras digestivas?";
    public static final String G10P9 = "¿Tiene problemas hepaticos?";

    public static final String G11P1 = "¿Le duelen las articulaciones?";
    public static final String G11P2 = "Pequeñas articulaciones";
    public static final String G11P3 = "Grandes articulaciones";
    public static final String G11P4 = "¿Toma algun medicamento?";
    public static final String G11P5 = CUAL;
    public static final String G11P6 = DESDE_CUANDO;

    public static final String G12P1 = "¿Tiene dificultad o presenta dolor al abrir la boca?";
    public static final String G12P2 = QUE;

    public static final String G13P1 = "¿Tuvo convulsiones?";
    public static final String G13P2 = DESDE_CUANDO;
    public static final String G13P3 = "¿Causa probable?";
    public static final String G13P4 = "¿Medicacion?";

    public static final String G14P1 = "¿Ha presentado o presenta perdida de sensibilidad"
            + " o movimientos en alguna parte del cuerpo?";
    public static final String G14P2 = CUAL;
    public static final String G14P3 = DESDE_CUANDO;
    public static final String G14P4 = TRATAMIENTO;

    public static final String G15P1 = "¿Padece epilepsia?";
    public static final String G15P2 = TRATAMIENTO;
    public static final String G15P3 = "¿Padece esclerosis en placa?";
    public static final String G15P4 = TRATAMIENTO;
    public static final String G15P5 = "¿Es muy nervioso?";
    public static final String G15P6 = "¿Toma sedantes o tranquilizantes?";
    public static final String G15P7 = CUAL;
    public static final String G15P8 = DESDE_CUANDO;
    public static final String G15P9 = "¿En que horarios?";
    public static final String G15P10 = "¿Toma medicamentos para dormir?";
    public static final String G15P11 = CUAL;
    public static final String G15P12 = DESDE_CUANDO;
    public static final String G15P13 = "¿Quien lo receto?";

    public static final String G16P1 = "¿Tiene problemas de piel y/o mucosas?";
    public static final String G16P2 = CUAL;
    public static final String G16P3 = DESDE_CUANDO;

    public static final String G17P1 = "¿Se le han efectuado transfusiones sanguineas?";
    public static final String G17P2 = CUANDO;
    public static final String G17P3 = PORQUE;
    public static final String G17P4 = DONDE;
    public static final String G17P5 = "¿Esta anticoagulado?";
    public static final String G17P6 = DESDE_CUANDO;
    public static final String G17P7 = "¿Medicacion?";

    public static final String G18P1 = "¿Ha padecido o padece cancer?";
    public static final String G18P2 = DE_QUE;
    public static final String G18P3 = "Radioterapia";
    public static final String G18P4 = "Quimioterapia";
    public static final String G18P5 = "Quirurgico";

    public static final String G19P1 = "¿Padece o padecio enfermedades de la sangre?";
    public static final String G19P2 = CUAL;
    public static final String G19P3 = DESDE_CUANDO;
    public static final String G19P4 = TRATAMIENTO;
    public static final String G19P5 = "¿Tiene pareja estable?";
    public static final String G19P6 = "¿Ha tenido alguna enfermedad en su aparato genital"
            + " que haya requerido atencion medica?";
    public static final String G19P7 = CUAL;
    public static final String G19P8 = CUANDO;
    public static final String G19P9 = TRATAMIENTO;

    public static final String G20P1 = "Controles ginecologicos";
    public static final String G20P2 = "Medidas anticoncepticas";
    public static final String G20P3 = CUALES;
    public static final String G20P4 = DESDE_CUANDO;
    public static final String G20P5 = "Ciclos Menstruales";
    public static final String G20P6 = "¿Esta embarazada?";
    public static final String G20P7 = "¿Cuantos meses?";
    public static final String G20P8 = "Controles periodicos";
    public static final String G20P9 = "¿Problemas durante el embarazo?";
    public static final String G20P10 = CUAL;
    public static final String G20P11 = TRATAMIENTO;
    public static final String G20P12 = "Amamanta";
    public static final String G20P13 = "Menopausia";
    public static final String G20P14 = TRATAMIENTO;

    public static final String G21P1 = "¿Es diabetico?";
    public static final String G21P2 = DESDE_CUANDO;
    public static final String G21P3 = "¿Conoce su tipo de diabetes?";
    public static final String G21P4 = "¿Se coloca insulina?";
    public static final String G21P5 = "Dosis diaria";
    public static final String G21P6 = "¿Toma hipoglucemiantes orales?";
    public static final String G21P7 = CUAL;
    public static final String G21P8 = "¿Dosis?";
    public static final String G21P9 = "¿Como se cuida y controla?";
    public static final String G21P10 = "Fecha del ultimo analisis";
    public static final String G21P11 = CUAL;
    public static final String G21P12 = "Valores";
    public static final String G21P13 = "¿Tiene familiares con diabetes?";

    public static final String G22P1 = "¿Es transplantado?";
    public static final String G22P2 = DE_QUE;
    public static final String G22P3 = "¿Toma medicamentos?";
    public static final String G22P4 = CUALES;
    public static final String G22P5 = "Dosis";

    public static final String G23P1 = "¿Consume bebidas alcoholicas habitualmente?";
    public static final String G23P2 = CANTIDAD_DIARIA;

    public static final String G24P1 = "¿Fuma actualmente?";
    public static final String G24P2 = CANTIDAD_DIARIA;

    public static final String G25P1 = "¿Consume drogas?";
    public static final String G25P2 = CUAL;
    public static final String G25P3 = "Por via inhalatoria";
    public static final String G25P4 = "Bucal";
    public static final String G25P5 = "Parenteral";

    public static final String G26P1 = "¿Se ha efectuado tatuajes?";
    public static final String G26P2 = "¿Se ha colocado piercing?";

    public static final String G27P1 = "¿Cual es su peso normal? (Kg)";
    public static final String G27P2 = "¿Perdio peso ultimamente?";
    public static final String G27P3 = "¿Cuanto? (Kg)";
    public static final String G27P4 = "¿En que tiempo?";

    public static final String G28P1 = "¿Cuando consulto por ultima vez a su medico?";
    public static final String G28P2 = "¿Motivo de la consulta?";
    public static final String G28P3 = "¿Le administraron medicamentos?";
    public static final String G28P4 = CUAL;
    public static final String G28P5 = "¿Le solicitaron analisis?";
    public static final String G28P6 = CUAL;

    public static final String G29P1 = "¿Tiene alguna enfermedad que quiera dejar constancia?";
    public static final String G29P2 = "¿Tiene tratamientos con medicina alternativa?";
    public static final String G29P3 = "Homeopatia";
    public static final String G29P4 = "Acupuntura";
    public static final String G29P5 = OTROS;


    //CONSTRUCTORS
    public DetalleHistoriaClinica() {
    }

    public DetalleHistoriaClinica(String nombre, Integer grupo, Integer pregunta) {
        this.nombre = nombre;
        this.grupo = grupo;
        this.pregunta = pregunta;
    }

    //GETTERS AND SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public Integer getPregunta() {
        return pregunta;
    }

    public void setPregunta(Integer pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleHistoriaClinica)) return false;
        if (!super.equals(o)) return false;

        DetalleHistoriaClinica that = (DetalleHistoriaClinica) o;

        if (grupo != null ? !grupo.equals(that.grupo) : that.grupo != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (pregunta != null ? !pregunta.equals(that.pregunta) : that.pregunta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (grupo != null ? grupo.hashCode() : 0);
        result = 31 * result + (pregunta != null ? pregunta.hashCode() : 0);
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
