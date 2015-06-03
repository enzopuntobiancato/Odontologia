/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

import java.security.Security;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Enzo
 */
public class SMTPConfig {

    /**
     * @param titulo : titulo del mensaje
     * @param mensaje : Cuerpo del Mensaje
     * @param paraEmail : Email receptor del mensaje
     * @return true si el envío es conforme y false si no es así.
     */
    public static synchronized boolean sendMail(Boolean enviar, String titulo, String mensaje, String paraEmail) throws MessagingException {
        if(enviar == false) {
            return false;
        }

            //carga del archivo smtp.properties
            final ResourceBundle props = ResourceBundle.getBundle("smtp");

            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //Propiedades de la conexion
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.transport.protocol", props.getString("mail.transport.protocol"));
            propiedades.setProperty("mail.host", props.getString("mail.host"));
            propiedades.put("mail.smtp.auth", props.getString("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getString("mail.smtp.port"));
            propiedades.put("mail.smtp.socketFactory.port", props.getString("mail.smtp.socketFactory.port"));
            propiedades.put("mail.smtp.socketFactory.class", props.getString("mail.smtp.socketFactory.class"));
            propiedades.put("mail.smtp.socketFactory.fallback", props.getString("mail.smtp.socketFactory.fallback"));
            propiedades.put("mail.smtp.mail.sender", props.getString("mail.smtp.mail.sender"));

            propiedades.setProperty("mail.smtp.quitwait", props.getString("mail.smtp.quitwait"));

            //Preparamos la Sesion autenticando al usuario
            Session session = Session.getDefaultInstance(propiedades, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getString("mail.user"), props.getString("mail.password"));
                }
            });

            //Preparamos el Mensaje
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(props.getString("mail.email")));
            message.setSubject(titulo);
            message.setContent(mensaje, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(props.getString("mail.smtp.mail.sender")));
            message.setReplyTo(InternetAddress.parse(props.getString("mail.smtp.mail.sender")));


            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(paraEmail));
            }
            
            //MimeMultipart multipart = new MimeMultipart();
            
            //BodyPart messageBodyPart = new MimeBodyPart();
            //String htmlText = mensaje;
            //messageBodyPart.setContent(htmlText, "text/html");
            
            //multipart.addBodyPart(messageBodyPart);
            
            //messageBodyPart = new MimeBodyPart();
            
                    //DataSource fds = new FileDataSource("src/main/resources/encabezadoMail.png");
                    //messageBodyPart.setDataHandler(new DataHandler(fds));
            //messageBodyPart.setHeader("Content-ID", "<image>");
            
            
            
            //multipart.addBodyPart(messageBodyPart);
            
            //message.setContent(multipart);
            //envío del mensaje
            Transport.send(message);

        return true;
    }
}
