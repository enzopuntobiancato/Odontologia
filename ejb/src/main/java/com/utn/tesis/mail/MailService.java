package com.utn.tesis.mail;

import java.util.Calendar;

public interface MailService {
    void sendBasicEmail(String toEmailAddress, String subject, String toName, String content);

    void sendRegistrationMail(String toAddress, String toName, String username, String password);

    void sendRecuperarPasswordMail(String toAddress, String toName, String username, String newPassword);

    void sendAsignacionConfirmadaMail(String toAddress,
                                      String pacienteApellido,
                                      String pacienteNombre,
                                      String alumnoApellido,
                                      String alumnoNombre,
                                      String alumnoEmail,
                                      Calendar fechaAsignacion,
                                      String catedra,
                                      String practica);

    void sendAsignacionConfirmadaCanceladaMail(String toAddress,
                                      String pacienteApellido,
                                      String pacienteNombre,
                                      String alumnoApellido,
                                      String alumnoNombre,
                                      String alumnoEmail,
                                      Calendar fechaAsignacion,
                                      String catedra,
                                      String practica);

    void sendAsignacionAutorizadaMail(String toAddress,
                                               String pacienteApellido,
                                               String pacienteNombre,
                                               String alumnoNombre,
                                               Calendar fechaAsignacion,
                                               String catedra,
                                               String practica,
                                               String trabajoPractico,
                                               String autorizadoPor);
}
