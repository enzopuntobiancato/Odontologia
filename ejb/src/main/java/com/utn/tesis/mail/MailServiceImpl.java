package com.utn.tesis.mail;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MailServiceImpl implements MailService {
    @Inject
    private MailSender mailSender;

    public void sendBasicEmail(String toEmailAddress, String subject, String toName, String content) {
        BasicMail email = BasicMail.builder()
                .toEmailAddress(toEmailAddress)
                .subject(subject)
                .toName(toName)
                .content(content)
                .build();

        SendingMailTask task = new SendingMailTask(email);
        mailSender.execute(task);
    }
}
