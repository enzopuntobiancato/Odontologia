package com.utn.tesis.mail;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MailServiceImpl implements MailService {
    @Inject
    private MailSender mailSender;

    private void sendEmail(IMail mail) {
        SendingMailTask task = new SendingMailTask(mail);
        mailSender.execute(task);
    }

    public void sendBasicEmail(String toEmailAddress, String subject, String toName, String content) {
        BasicMail email = BasicMail.builder()
                .toEmailAddress(toEmailAddress)
                .subject(subject)
                .toName(toName)
                .content(content)
                .build();

        sendEmail(email);
    }

    @Override
    public void sendRegistrationMail(String toAddress, String toName, String username, String password) {
        RegistrationMail email = RegistrationMail.builder()
                .toName(toName)
                .toAddress(toAddress)
                .username(username)
                .password(password)
                .build();

        sendEmail(email);
    }
}
