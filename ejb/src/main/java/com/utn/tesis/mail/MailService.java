package com.utn.tesis.mail;

public interface MailService {
    void sendBasicEmail(String toEmailAddress, String subject, String toName, String content);
    void sendRegistrationMail(String toAddress, String toName, String username, String password);
    void sendRecuperarPasswordMail(String toAddress, String toName, String username, String newPassword);
}
