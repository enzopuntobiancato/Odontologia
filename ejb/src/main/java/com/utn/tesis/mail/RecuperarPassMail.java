package com.utn.tesis.mail;

import lombok.experimental.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class RecuperarPassMail implements IMail {
    private final String toAddress;
    private final String toName;
    private final String username;
    private final String newPassword;

    @Override
    public String getToEmailAddress() {
        return toAddress;
    }

    @Override
    public String getTemplate() {
        return "recuperarPass.ftl";
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return new HashMap<String, String>(){{
            put("user", toName);
            put("nombreUsuario", username);
            put("contrasenia", newPassword);
        }};
    }

    @Override
    public String getSubject() {
        return "Recuperar contraseña";
    }
}
