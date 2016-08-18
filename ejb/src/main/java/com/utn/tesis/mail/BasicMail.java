package com.utn.tesis.mail;

import lombok.experimental.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
class BasicMail implements IMail {
    private final String toEmailAddress;
    private final String subject;
    private final String toName;
    private final String content;

    public BasicMail(String toEmailAddress, String subject, String toName, String content) {
        this.toEmailAddress = toEmailAddress;
        this.subject = subject;
        this.toName = toName;
        this.content = content;
    }

    @Override
    public String getToEmailAddress() {
        return this.toEmailAddress;
    }

    @Override
    public String getTemplate() {
        return "basic-template.ftl";
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return new HashMap<String, String>(){{
            put("user", toName);
            put("content", content);
        }};
    }

    @Override
    public String getSubject() {
        return this.subject;
    }
}
