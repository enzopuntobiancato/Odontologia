package com.utn.tesis.mail;

import java.util.Map;

interface IMail {
    String getToEmailAddress();
    String getTemplate();
    Map<String, String> getTemplateValues();
    String getSubject();
}
