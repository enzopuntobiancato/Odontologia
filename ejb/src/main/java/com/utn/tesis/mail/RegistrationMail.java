package com.utn.tesis.mail;

import lombok.experimental.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public class RegistrationMail implements IMail {
    private final String toAddress;
    private final String toName;
    private final String username;
    private final String password;

    @Override
    public String getToEmailAddress() {
        return toAddress;
    }

    @Override
    public String getTemplate() {
        return "registration.ftl";
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return new HashMap<String, String>(){{
            put("user", toName);
            put("nombreUsuario", username);
            put("contrasenia", password);
        }};
    }

    @Override
    public String getSubject() {
        return "Te has registrado en SAPO";
    }
}
