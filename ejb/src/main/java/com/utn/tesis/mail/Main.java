/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

import com.utn.tesis.util.EncryptionUtils;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Enzo
 */
public class Main {
    
    public static void main(String [ ] args)
    {

        ExecutorService executor = Executors.newFixedThreadPool(10);


        Alumno alumno = new Alumno();

        RegisterMail mail = new RegisterMail(alumno);
        mail.buildMail();




         //for (int i = 0; i < 2; i++) {
            MailService newMail = new MailService(mail);
            executor.execute(newMail);

        //   System.out.println("Mail " + i + " disparado.");
        System.out.println("Mail disparado.");
        //}


        System.out.println("Lalalalala");
        try {
            System.out.println(EncryptionUtils.encryptMD5A1("admin"));
            System.out.println(EncryptionUtils.encryptMD5A1("123"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println(Alumno.class.getName());
        System.out.println(Alumno.class.getCanonicalName());
        System.out.println(Alumno.class.getSimpleName());

    }
    
}
