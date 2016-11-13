package com.utn.tesis.mail;

import lombok.experimental.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class AsignacionAutorizadaMail implements IMail {
    private final String toAddress;
    private final String pacienteApellido;
    private final String pacienteNombre;
    private final String alumnoNombre;
    private final String fechaAsignacion;
    private final String catedraAsignacion;
    private final String practicaAsignacion;
    private final String trabajoPractico;
    private final String autorizadoPor;

    @Override
    public String getToEmailAddress() {
        return toAddress;
    }

    @Override
    public String getTemplate() {
        return "asignacionAutorizada.ftl";
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return new HashMap<String, String>(){{
            put("pacienteApellido", pacienteApellido);
            put("pacienteNombre", pacienteNombre);
            put("alumnoNombre", alumnoNombre);
            put("fechaAsignacion", fechaAsignacion);
            put("catedraAsignacion", catedraAsignacion);
            put("practicaAsignacion", practicaAsignacion);
            put("trabajoPractico", trabajoPractico);
            put("autorizadoPor", autorizadoPor);
        }};
    }

    @Override
    public String getSubject() {
        return "Asignación de paciente autorizada";
    }
}
