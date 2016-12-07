package com.utn.tesis.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

@Slf4j
public class SendingMailTask implements Runnable {

    private final ResourceBundle bundleProperties = ResourceBundle.getBundle("smtp");
    private final IMail mail;

    public SendingMailTask(IMail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {
        try {
            log.info("Sending email to {}", mail.getToEmailAddress());
            final Properties properties = getPropertiesFromBundle();

            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setSubject(mail.getSubject());
            message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.mail.sender"), "SAPO Odontología"));
            message.setReplyTo(InternetAddress.parse(properties.getProperty("mail.smtp.mail.sender")));

            if (mail.getToEmailAddress().indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getToEmailAddress()));
            } else {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToEmailAddress()));
            }

            Configuration fmConfig = new Configuration();
            fmConfig.setDefaultEncoding("UTF-8");
            fmConfig.setClassForTemplateLoading(this.getClass(), "/templates");
            Template template = fmConfig.getTemplate(mail.getTemplate());

            StringWriter sw = new StringWriter();

            String cid = "logoImage";
            // Agregamos el logo
            Map<String, String> templateValues = mail.getTemplateValues();
            templateValues.put("logoUrl", "cid:" + cid);
            template.process(templateValues, sw);

            MimeMultipart content = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(sw.toString(), "text/html; charset=utf-8");
            content.addBodyPart(htmlPart);

            MimeBodyPart logoPart = new MimeBodyPart();
            File file = new File(this.getClass().getClassLoader().getResource("sapo1.png").getFile());
            logoPart.attachFile(file);
            logoPart.setContentID("<" + cid + ">");
            logoPart.setDisposition(MimeBodyPart.INLINE);
            content.addBodyPart(logoPart);

            message.setContent(content);
            Transport.send(message);
            log.info("Email correctly sent to {}", mail.getToEmailAddress());
        } catch (Exception e) {
            log.error("Error sending email", e);
        }
    }

    private Properties getPropertiesFromBundle() {
        Properties properties = new Properties();

        Enumeration<String> keys = bundleProperties.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, bundleProperties.getString(key));
        }

        return properties;
    }
}
