package com.utn.tesis.mail;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@ApplicationScoped
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

    @Override
    public void sendRecuperarPasswordMail(String toAddress, String toName, String username, String newPassword) {
        RecuperarPassMail email = RecuperarPassMail.builder()
                .toAddress(toAddress)
                .toName(toName)
                .username(username)
                .newPassword(newPassword)
                .build();
        sendEmail(email);
    }

    @Override
    public void sendAsignacionConfirmadaMail(String toAddress,
                                             String pacienteApellido,
                                             String pacienteNombre,
                                             String alumnoApellido,
                                             String alumnoNombre,
                                             String alumnoEmail,
                                             Calendar fechaAsignacion,
                                             String catedra,
                                             String practica) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        AsignacionConfirmadaMail email = AsignacionConfirmadaMail.builder()
                .toAddress(toAddress)
                .pacienteApellido(pacienteApellido)
                .pacienteNombre(pacienteNombre)
                .alumnoApellido(alumnoApellido)
                .alumnoNombre(alumnoNombre)
                .alumnoEmail(alumnoEmail)
                .fechaAsignacion(sdf.format(fechaAsignacion.getTime()))
                .catedraAsignacion(catedra)
                .practicaAsignacion(practica)
                .build();

        sendEmail(email);
    }

    @Override
    public void sendAsignacionConfirmadaCanceladaMail(String toAddress, String pacienteApellido, String pacienteNombre, String alumnoApellido, String alumnoNombre, String alumnoEmail, Calendar fechaAsignacion, String catedra, String practica) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        AsignacionConfirmadaCanceladaMail email = AsignacionConfirmadaCanceladaMail.builder()
                .toAddress(toAddress)
                .pacienteApellido(pacienteApellido)
                .pacienteNombre(pacienteNombre)
                .alumnoApellido(alumnoApellido)
                .alumnoNombre(alumnoNombre)
                .alumnoEmail(alumnoEmail)
                .fechaAsignacion(sdf.format(fechaAsignacion.getTime()))
                .catedraAsignacion(catedra)
                .practicaAsignacion(practica)
                .build();

        sendEmail(email);
    }

    @Override
    public void sendAsignacionAutorizadaMail(String toAddress, String pacienteApellido, String pacienteNombre, String alumnoNombre, Calendar fechaAsignacion, String catedra, String practica, String trabajoPractico, String autorizadoPor) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        AsignacionAutorizadaMail email = AsignacionAutorizadaMail.builder()
                .toAddress(toAddress)
                .pacienteApellido(pacienteApellido)
                .pacienteNombre(pacienteNombre)
                .alumnoNombre(alumnoNombre)
                .fechaAsignacion(sdf.format(fechaAsignacion.getTime()))
                .catedraAsignacion(catedra)
                .practicaAsignacion(practica)
                .trabajoPractico(trabajoPractico)
                .autorizadoPor(autorizadoPor)
                .build();

        sendEmail(email);
    }
}
