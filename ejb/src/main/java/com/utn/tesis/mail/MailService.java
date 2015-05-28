/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enzo
 */
public class MailService {


//    http://www.softwareengineeringsolutions.com/blogs/2010/07/21/implementing-thread-pools-using-java-executors/
    private static final int MAX_ALLOWED_THREADS = 5;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_ALLOWED_THREADS);
    
    public static void sendEmail(final Mail mail)
    {
        
        
        
        try {
            executor.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        SMTPConfig.sendMail(Boolean.TRUE, "Te has registrado en SAPO", mail.getMail(), "enzo.biancato@gmail.com");
                        System.out.println("Mensaje enviado.");
                    } catch (MessagingException ex) {
                        Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
