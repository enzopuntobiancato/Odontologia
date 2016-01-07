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
        builder.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Bienvenido a SAPO</title>\n" +
                stringStyle +
                "    <meta author=\"Maximiliano Barros\" />\n" +
                "    <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\" />\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"view\">\n" +
                "      <img src=\"http://i.imgur.com/TfOYNF0.png?1\" />\n" +
                "      <div class=\"mask\">\n" +
                "        <div class=\"titulo\">\n" +
                "          <h1>BIENVENIDO</h1>\n" +
                "        </div>\n" +
                "        <div class=\"contenido\">\n" +
                "          <h3>\n" +
                "\t\t  <br> Estimado "+alumno.getNombre().toUpperCase()+". </br>\n" +
                "\t\t  <br> Te damos la bienvenida a SAPO. </br>\n" +
                "\t\t\t<br> Este sistema te permite buscar pacientes para tus prácticas de manera rápida y sencilla, así como hacer un seguimiento de los mismos.\n" +
                "\t\t\tEsperamos que lo encuentres de utilidad y te facilite tus tareas académicas relacionadas con la atención de pacientes.\n" +
                "\t\t\tTe recordamos que para que el objetivo de este sistema, el cual es ayudarte, necesita de vos para poder realizarse. Esperamos contar con tu colaboración. \n" +
                "\t\t\t</br>\n" +
                "\t\t\t<br> Los datos para que puedas iniciar sesión son los siguientes:\n" +
                "\t\t  </h3>\n" +
                "          <div class=\"username\">\n" +
                "\t\t\t<p>USUARIO: "+alumno.getUsuario()+"</p>\n" +
                "\t\t\t<p>CONTRASEÑA: "+alumno.getContraseña()+"</p>\n" +
                "\t\t\t</div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n");
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


    private static String stringStyle =
            "<style type=\"text/css\">\n" +
            "  \n" +
            "  .view {\n" +
            "   width: 958px;\n" +
            "   height: 756px;\n" +
            "}\n" +
            ".mask {\n" +
            "\ttop: 200px;\n" +
            "\tleft: 129px;\n" +
            "   width: 700px;\n" +
            "   height: 500px;\n" +
            "   position: absolute;\n" +
            "   overflow: hidden;\n" +
            "   background: rgba(175, 255, 225, 0.5);\n" +
            "   border-radius: 25px;\n" +
            "\tborder: 3px solid rgba(175, 255, 225, 0.3);\n" +
            "}\n" +
            ".titulo {\n" +
            "\tfont-family: Consolas, monaco, monospace;\n" +
            "\tfont-size: 10px;\n" +
            "\tfont-style: normal;\n" +
            "\tfont-variant: normal;\n" +
            "\tfont-weight: bold;\n" +
            "\tline-height: 10px;\n" +
            "\twidth: 20%;\n" +
            "   color: #CCCCCC;\n" +
            "   text-align: center;\n" +
            "   position: relative;\n" +
            "   padding: 5px;\n" +
            "   background: rgba(0, 0, 0, 0.6);\n" +
            "   border-radius: 0px 0px 0px 25px;\n" +
            "   margin-left: auto;\n" +
            "}\n" +
            ".contenido {\n" +
            "   font-family: Georgia, serif;\n" +
            "   font-style: italic;\n" +
            "   font-size: 13px;\n" +
            "   width: 80%;\n" +
            "   border-radius: 25px;\n" +
            "   position: relative;\n" +
            "   color: #fff;\n" +
            "   padding: 10px 20px 20px;\n" +
            "   text-align: left;\n" +
            "   background: rgba(125, 0, 125, 0.8);\n" +
            "   margin: 20px auto;\n" +
            "}\n" +
            ".username {\n" +
            "\tfont-family: Georgia, serif;\n" +
            "   font-style: italic;\n" +
            "   font-size: 14px;\n" +
            "   width: 50%;\n" +
            "   border-radius: 15px;\n" +
            "   position: relative;\n" +
            "   color: #fff;\n" +
            "   padding: 10px 10px 10px 10px;\n" +
            "   text-align: rigth;\n" +
            "   background: rgba(0, 0, 0, 0.9);\n" +
            "   margin: auto;\n" +
            "}\n" +
            "\t</style>\n"
            ;
}

