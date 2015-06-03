/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

/**
 *
 * @author Enzo
 */
public class RegisterMail extends Mail {

    private Alumno alumno;
    public RegisterMail(Alumno alumno)
   {
        super();
        this.alumno = alumno;
    }


    @Override
    protected void setVariableContent() {
        StringBuilder builder = new StringBuilder();
        super.setTitle("<h1>Bienvenido</h1>");
        builder.append("<p style=\"text-align: justify;\" >Estimado ").append(alumno.getNombre()).append(", te damos la bienvenido a SAPO. ");
        builder.append("Este sistema te permite buscar pacientes para tus prácticas de manera rápida y sencilla, así como hacer un seguimiento de los mismos. ");
        builder.append("Esperamos que lo encuentres de utilidad y te facilite tus tareas académicas relacionadas con la atención de pacientes. ");
        builder.append("Te recordamos que para que el objetivo de este sistema, el cual es ayudarte, necesita de vos para poder realizarse. Esperamos contar con tu colaboración.<br/>");
        builder.append("<br/>");
        builder.append("Los datos para que puedas iniciar sesión son los siguientes:<br/>");
        builder.append("<hr/>");
        builder.append("<blockquote> <b> Nombre de usuario: ").append(alumno.getUsuario()).append("</b></blockquote>");
        builder.append("<blockquote> <b> Contraseña: ").append(alumno.getContraseña()).append("</b></blockquote>");
        builder.append("<hr/>");
        builder.append("<br/>");
        builder.append("Atte. <br/><br/> El equipo de SAPO.</p>");
        super.setContent(builder.toString());
    }

    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


}

