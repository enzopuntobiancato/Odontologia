package com.utn.tesis.mail;

import lombok.experimental.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class AsignacionConfirmadaMail implements IMail {
    private final String toAddress;
    private final String pacienteApellido;
    private final String pacienteNombre;
    private final String alumnoApellido;
    private final String alumnoNombre;
    private final String alumnoEmail;
    private final String fechaAsignacion;
    private final String catedraAsignacion;
    private final String practicaAsignacion;

    @Override
    public String getToEmailAddress() {
        return toAddress;
    }

    @Override
    public String getTemplate() {
        return "asignacionConfirmada.ftl";
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return new HashMap<String, String>(){{
            put("pacienteApellido", pacienteApellido);
            put("pacienteNombre", pacienteNombre);
            put("alumnoApellido", alumnoApellido);
            put("alumnoNombre", alumnoNombre);
            put("alumnoEmail", alumnoEmail);
            put("fechaAsignacion", fechaAsignacion);
            put("catedraAsignacion", catedraAsignacion);
            put("practicaAsignacion", practicaAsignacion);
        }};
    }

    @Override
    public String getSubject() {
        return "Asignación a tu nombre confirmada";
    }
}
