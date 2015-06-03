/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

import javax.mail.MessagingException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enzo
 */
public class MailService implements Runnable {

    private Mail mail;

    public MailService(final Mail mail) {
        this.mail = mail;
    }


    @Override
    public void run() {
        try {
            SMTPConfig.sendMail(Boolean.TRUE, "Te has registrado en SAPO", mail.getMail(), "maxibarros@gmail.com");
            System.out.println("Mensaje enviado.");
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
