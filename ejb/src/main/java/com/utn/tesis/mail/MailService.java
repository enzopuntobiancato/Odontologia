package com.utn.tesis.mail;

public interface MailService {
    void sendBasicEmail(String toEmailAddress, String subject, String toName, String content);
}
