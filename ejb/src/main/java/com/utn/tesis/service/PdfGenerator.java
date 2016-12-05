package com.utn.tesis.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.utn.tesis.model.*;
import com.utn.tesis.util.FechaUtils;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

@ApplicationScoped
public class PdfGenerator {
    private static final Font HcFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.UNDERLINE);
    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font questionFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static final Font answerFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

    public File createHCPDF(Paciente paciente) throws IOException {
        File file = File.createTempFile(paciente.getApellido(), paciente.getApellido() + "_" + paciente.getNombre());
        createPDF(file, paciente);
        return file;
    }

    public File createAsignacionesListPdf(ArrayList<AsignacionPaciente> asignaciones) throws IOException {
        File file = File.createTempFile("asignaciones", "autorizar");
        crearListaAsignaciones(file, asignaciones);
        return file;
    }

    public File createImpresionAtencion(Atencion atencion) throws IOException {
        File file = File.createTempFile(atencion.getAsignacionPaciente().getAlumno().getApellido() +
                " " + atencion.getAsignacionPaciente().getAlumno().getNombre() +
                " Atencion Nº " + atencion.getId().toString() , "Atencion");
        crearAtencion(file, atencion);
        return file;
    }

    private void crearAtencion(File pdfNewFile, Atencion a) {
        if (a == null) {
            return;
        }
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            try {
                document.open();
                PdfPTable table = table();
                table.addCell(cell(2,
                        new Chunk("Fecha Atencion: ", questionFont),
                        new Chunk(text(FechaUtils.fechaConSeparador(a.getFechaAtencion())), answerFont)));
                table.addCell(cell(2,
                        new Chunk("Fecha de Carga: ", questionFont),
                        new Chunk(text(FechaUtils.fechaConSeparador(a.getFechaDeCarga())), answerFont)));
                table.addCell(cell(2,
                        new Chunk("Alumno: ", questionFont),
                        new Chunk(text(a.getAsignacionPaciente().getAlumno().getApellido()
                                + ", " + text(a.getAsignacionPaciente().getAlumno().getNombre())), answerFont)));
                table.addCell(cell(2,
                        new Chunk("Profesor Autorizante: ", questionFont),
                        new Chunk(text(a.getAsignacionPaciente().getAutorizadoPor().getApellido()
                                + ", " + text(a.getAsignacionPaciente().getAutorizadoPor().getNombre())), answerFont)));
                table.addCell(cell(2,
                        new Chunk("Numero de Diagnostico: ", questionFont),
                        new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getId()), answerFont)));
                if (nullOVacio(a.getAsignacionPaciente().getDiagnostico().getPracticaNoExistente())) {
                    table.addCell(cell(2,
                            new Chunk("Practica Odontologica: ", questionFont),
                            new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getPracticaOdontologica().getDenominacion()), answerFont)));
                } else {
                    table.addCell(cell(2,
                            new Chunk("Practica Odontologica: ", questionFont),
                            new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getPracticaNoExistente()), answerFont)));
                }
                table.addCell(cell(4,
                        new Chunk("Descripción de Procedimiento: ", questionFont),
                        new Chunk(text(a.getDescripcionProcedimiento()), answerFont)));
                table.addCell(cell(4,
                        new Chunk("Diagnostico Solucionado: ", questionFont),
                        new Chunk(a.isDiagnosticoSolucionado() ? "Si" : "No", answerFont)));
                document.add(table);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            document.close();
            System.out.println("PDF Generado con exito!");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }


    private void crearListaAsignaciones(File pdfNewFile, ArrayList<AsignacionPaciente> asignaciones) {
        if(asignaciones == null) {
            asignaciones = new ArrayList<AsignacionPaciente>();
        }
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }

            try {
                document.open();
                Paragraph par = new Paragraph();

                String uncheckStr = this.getClass().getClassLoader().getResource("hcpdf/unchecked_checkbox.png").getFile();
                Image uncheck = Image.getInstance(uncheckStr);

                separator(par, "Listado de Asignaciones para Autorizar");

                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(100);
                table.setSpacingAfter(5f);
                table.setSpacingBefore(5f);
                table.addCell(cell(1,new Chunk("ID", questionFont)));
                table.addCell(cell(1,new Chunk("Alumno", questionFont)));
                table.addCell(cell(1,new Chunk("Paciente", questionFont)));
                table.addCell(cell(1,new Chunk("Estado", questionFont)));
                table.addCell(cell(1,new Chunk("Trabajo Practico", questionFont)));
                table.addCell(cell(1,new Chunk("Fecha Asignacion", questionFont)));
                table.addCell(cell(1,new Chunk("Autorizado", questionFont)));

                for (AsignacionPaciente a : asignaciones) {

                    table.addCell(cell(1,new Chunk(text(a.getId()), answerFont)));
                    table.addCell(cell(1,new Chunk(text(a.getAlumno().getApellido()+ ", "
                            + text(a.getAlumno().getNombre())), answerFont)));
                    table.addCell(cell(1,new Chunk(text(a.getPaciente().getApellido()+ ", "
                            + text(a.getPaciente().getNombre())), answerFont)));
                    table.addCell(cell(1,new Chunk(text(a.getUltimoMovimiento().getEstado()), answerFont)));
                    table.addCell(cell(1,new Chunk(text(a.getTrabajoPractico().getNombre()), answerFont)));
                    table.addCell(cell(1,new Chunk(text(FechaUtils.fechaConSeparador(a.getFechaAsignacion())), answerFont)));
                    table.addCell(cell(1,new Chunk(uncheck, 20, -5, true)));
                    par.add(table);
                }
                par.add(new Chunk(Chunk.NEWLINE));

                document.add(par);
            } catch (BadElementException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            document.close();
            System.out.println("PDF Generado con exito!");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }

    public void crearBonoConsultaPDF(File pdfNewFile) {
        try {
            Document document = new Document(PageSize.A6);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }

            try {
                document.open();
                Paragraph par = new Paragraph();
                par.setAlignment(Element.ALIGN_CENTER);

                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100);
                table.setSpacingAfter(5f);
                table.setSpacingBefore(5f);

                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk("Bono de consulta", HcFont));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk("Fecha: ", questionFont));
                par.add(new Chunk(text(FechaUtils.fechaConSeparador(Calendar.getInstance())), answerFont));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk("Bono Nº: " + UUID.randomUUID().toString(), answerFont));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));

                table.addCell(cell(1,par));


                document.add(table);
            } catch (BadElementException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            document.close();
            System.out.println("PDF Generado con exito!");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }

    }

    private void separator(Paragraph p, String title) {
        p.add(new Chunk(Chunk.NEWLINE));
        if (title != null) {
            p.add(new Chunk(title, titleFont));
        }
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(3f);
        p.add(dottedline);
        p.add(new Chunk(Chunk.NEWLINE));
    }

    private boolean nullOVacio(Object o) {
        if (o == null) return true;
        if (o.toString().isEmpty()) return true;
        return false;
    }

    private String text(Object o) {
        if (o == null) return "No definido";
        if (o.toString() == null || o.toString().isEmpty()) return "No definido";
        return o.toString();
    }

    private Paragraph campoDetalleDraw(Paragraph p, String preg, String resp) throws DocumentException {
        //Para salvar nulls
        if (resp == null)
            resp = "No definido";
        p.add(new Chunk(preg + ": ", questionFont));
        p.add(Chunk.NEWLINE);
        p.add((new Chunk(resp, answerFont)));
        p.add(Chunk.NEWLINE);
        return p;
    }

    private Paragraph campoSiNoDraw(Paragraph p, String preg, Boolean sino) throws DocumentException, IOException {
        //Para salvar nulls
        if (sino == null)
            sino = false;

        p.add(new Chunk(preg + ": ", questionFont));
        p.add(Chunk.NEWLINE);
        String checkStr = this.getClass().getClassLoader().getResource("hcpdf/checked_checkbox.png").getFile();
        String uncheckStr = this.getClass().getClassLoader().getResource("hcpdf/unchecked_checkbox.png").getFile();

        Image check = Image.getInstance(checkStr);
        Image uncheck = Image.getInstance(uncheckStr);
        if (sino) {
            p.add(new Chunk(check, 0, -3, true));
            p.add(new Chunk(" Si     ", answerFont));
            p.add(new Chunk(uncheck, 0, -3, true));
            p.add(new Chunk(" No", answerFont));
        } else {
            p.add(new Chunk(uncheck, 0, -3, true));
            p.add(new Chunk(" Si     ", answerFont));
            p.add(new Chunk(check, 0, -3, true));
            p.add(new Chunk(" No", answerFont));
        }
        p.add(Chunk.NEWLINE);
        return p;
    }

    private Paragraph campoEnumerableDraw(Paragraph p, String enu, Boolean checked) throws DocumentException, IOException {
        //Para salvar nulls
        if (checked == null)
            checked = false;

        String checkStr = this.getClass().getClassLoader().getResource("hcpdf/checked_checkbox.png").getFile();
        String uncheckStr = this.getClass().getClassLoader().getResource("hcpdf/unchecked_checkbox.png").getFile();

        Image check = Image.getInstance(checkStr);
        Image uncheck = Image.getInstance(uncheckStr);
        if (checked) {
            p.add(Chunk.NEWLINE);
            p.add(new Chunk(check, 0, -3, true));
        } else {
            p.add(Chunk.NEWLINE);
            p.add(new Chunk(uncheck, 0, -3, true));
        }
        p.add(new Chunk(" " + enu, answerFont));
        p.add(Chunk.NEWLINE);
        return p;
    }

    private Paragraph campoFechaDraw(Paragraph p, String preg, Calendar fecha) {
        p.add(Chunk.NEWLINE);
        p.add(new Chunk(preg + ": ", questionFont));
        p.add(Chunk.NEWLINE);
        if (fecha == null)
            p.add(new Chunk("No definido", answerFont));
        else
            p.add(new Chunk(FechaUtils.fechaConSeparador(fecha), answerFont));
        p.add(Chunk.NEWLINE);
        return p;
    }

    private PdfPTable table() {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingAfter(5f);
        table.setSpacingBefore(5f);
        return table;
    }

    private PdfPCell cell(int colspan, Element... elements) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(Rectangle.BOX);
        Paragraph p = new Paragraph();
        p.setSpacingAfter(7f);
        for (Element e : elements) {
            p.add(e);
        }
        cell.addElement(p);
        return cell;
    }

    private void createPDF(File pdfNewFile, Paciente p) {
        if (p == null)
            return;

        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }

            try {
                document.open();
                Paragraph par = new Paragraph();
                par.setAlignment(Element.ALIGN_CENTER);
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk("Historia Clinica", HcFont));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk("Fecha Apertura de Historia Clinica: ", questionFont));
                par.add(new Chunk(text(FechaUtils.fechaConSeparador(p.getHistoriaClinica().getFechaApertura())), answerFont));
                par.add(new Chunk("            Historia Clinica Nº: ", questionFont));
                par.add(new Chunk(text(p.getHistoriaClinica().getId()), answerFont));
                par.add(new Chunk(Chunk.NEWLINE));
                par.add(new Chunk(Chunk.NEWLINE));

                document.add(par);

                if(p.getImagen() != null) {
                    Image iTextImage = Image.getInstance(new File(p.getImagen().getRuta()).toURI().toURL());
                    if(iTextImage != null) {
                        iTextImage.scaleAbsolute(80, 80);
                        iTextImage.setAbsolutePosition(480, 610);
                        document.add(iTextImage);
                    }
                }

                Paragraph par1 = new Paragraph();
                separator(par1, "Datos Personales");
                par1.add(new Chunk("Apellido y Nombre: ", questionFont));
                par1.add(new Chunk(text(p.getApellido()) + ", " + text(p.getNombre()), answerFont));

                par1.add(new Chunk(" Documento: ", questionFont));
                if (p.getDocumento() == null || (nullOVacio(p.getDocumento().getTipoDocumento()) || nullOVacio(p.getDocumento().getNumero())))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDocumento().getTipoDocumento()) + " " + text(p.getDocumento().getNumero()), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                par1.add(new Chunk("Sexo: ", questionFont));
                par1.add(new Chunk(text(p.getSexo()), answerFont));

                par1.add(new Chunk(" Fecha de Nacimiento: ", questionFont));
                par1.add(new Chunk(text(FechaUtils.fechaConSeparador(p.getFechaNacimiento())), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                par1.add(new Chunk("Nacionalidad: ", questionFont));
                if (nullOVacio(p.getNacionalidad()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getNacionalidad()), answerFont));

                par1.add(new Chunk(" Nivel de Estudios: ", questionFont));
                if (nullOVacio(p.getNivelEstudio()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getNivelEstudio()), answerFont));

                par1.add(new Chunk(" Estado Civil: ", questionFont));
                if (nullOVacio(p.getEstadoCivil()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getEstadoCivil()), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                par1.add(new Chunk("Mail: ", questionFont));
                par1.add(new Chunk(text(p.getEmail()), answerFont));

                par1.add(new Chunk(" Telefono: ", questionFont));
                par1.add(new Chunk(text(p.getTelefono()), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                par1.add(new Chunk("Domicilio: ", questionFont));
                if (p.getDomicilio() == null || (nullOVacio(p.getDomicilio().getCalle()) || nullOVacio(p.getDomicilio().getNumero())))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDomicilio().getCalle()) + " " + text(p.getDomicilio().getNumero()), answerFont));
                par1.add(new Chunk(" Barrio: ", questionFont));
                if (p.getDomicilio() == null || (nullOVacio(p.getDomicilio().getBarrio())))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDomicilio().getBarrio().getNombre()), answerFont));

                par1.add(new Chunk(" Codigo Postal: ", questionFont));
                if (nullOVacio(p.getDomicilio()) || nullOVacio(p.getDomicilio().getCodigoPostal()))
                    par1.add(new Chunk("" + text(null), answerFont));
                else
                    par1.add(new Chunk("" + p.getDomicilio().getCodigoPostal(), answerFont));
                par1.add(new Chunk(Chunk.NEWLINE));

                par1.add(new Chunk("Departamento: ", questionFont));
                if (nullOVacio(p.getDomicilio()) || nullOVacio(p.getDomicilio().getDepartamento()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDomicilio().getDepartamento()), answerFont));

                par1.add(new Chunk(" Ciudad: ", questionFont));
                if (nullOVacio(p.getDomicilio()) || nullOVacio(p.getDomicilio().getBarrio()) || nullOVacio(p.getDomicilio().getBarrio().getCiudad()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDomicilio().getBarrio().getCiudad().getNombre()), answerFont));

                par1.add(new Chunk(" Provincia: ", questionFont));
                if (nullOVacio(p.getDomicilio()) || nullOVacio(p.getDomicilio().getBarrio()) || nullOVacio(p.getDomicilio().getBarrio().getCiudad())
                        || nullOVacio(p.getDomicilio().getBarrio().getCiudad().getProvincia()))
                    par1.add(new Chunk(text(null), answerFont));
                else
                    par1.add(new Chunk(text(p.getDomicilio().getBarrio().getCiudad().getProvincia().getNombre()), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                par1.add(new Chunk("Trabajo: ", questionFont));
                par1.add(new Chunk(text(p.getTrabajo()), answerFont));

                par1.add(new Chunk(" Religion: ", questionFont));
                par1.add(new Chunk(text(p.getReligion()), answerFont));

                par1.add(new Chunk(Chunk.NEWLINE));
                separator(par1, "Datos Médicos");
                par1.add(new Chunk("Medico de Cabecera: ", questionFont));
                par1.add(new Chunk(text(p.getMedicoCabecera()), answerFont));

                par1.add(new Chunk(" Tel. Medico de Cabecera: ", questionFont));
                par1.add(new Chunk(text(p.getTelefonoMedicoCabecera()), answerFont));
                par1.add(new Chunk(Chunk.NEWLINE));

                par1.add(new Chunk("Obra Social: ", questionFont));
                par1.add(new Chunk(text(p.getObraSocial()), answerFont));

                par1.add(new Chunk(" Servicio de Emergencia: ", questionFont));
                par1.add(new Chunk(text(p.getServicioEmergencia()), answerFont));

                document.add(par1);

                Paragraph par2 = new Paragraph();
                separator(par2, "Diagnosticos");
                par2.add(new Chunk(Chunk.NEWLINE));

                String uri = p.getHistoriaClinica().getOdontogramaUri();
                if (uri == null) {
                    uri = HistoriaClinica.EMPTY_URI;
                }
                String base64Image = uri.split(",")[1];
                try {
                    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                    Image iTextImage = Image.getInstance(imageBytes);
                    iTextImage.scaleAbsolute(560, 150);
                    par2.add(iTextImage);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                for (Diagnostico d : p.getHistoriaClinica().getDiagnostico()) {
                    PdfPTable table = table();
                    table.addCell(cell(2,
                            new Chunk("Numero de Diagnostico: ", questionFont),
                            new Chunk(text(d.getId()), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Fecha de Creacion: ", questionFont),
                            new Chunk(text(FechaUtils.fechaConSeparador(d.getFechaCreacion())), answerFont)));
                    table.addCell(cell(4,
                            new Chunk("Observaciones: ", questionFont),
                            new Chunk(text(d.getObservaciones()), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Estado del Diagnostico: ", questionFont),
                            new Chunk(text(d.getUltimoMovimiento().getEstado()), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Fecha de Actualizacion del Estado: ", questionFont),
                            new Chunk(text(FechaUtils.fechaConSeparador(d.getUltimoMovimiento().getFechaMovimiento())), answerFont)));
                    table.addCell(cell(4,
                            new Chunk("Observaciones de Estado: ", questionFont),
                            new Chunk(text(d.getUltimoMovimiento().getObservaciones()), answerFont)));
                    par2.add(table);
                }
                par2.add(new Chunk(Chunk.NEWLINE));
                par2.add(new Chunk(Chunk.NEWLINE));

                separator(par2, "Atenciones");
                for (Atencion a : p.getHistoriaClinica().getAtencion()) {
                    PdfPTable table = table();
                    table.addCell(cell(2,
                            new Chunk("Fecha Atencion: ", questionFont),
                            new Chunk(text(FechaUtils.fechaConSeparador(a.getFechaAtencion())), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Fecha de Carga: ", questionFont),
                            new Chunk(text(FechaUtils.fechaConSeparador(a.getFechaDeCarga())), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Alumno: ", questionFont),
                            new Chunk(text(a.getAsignacionPaciente().getAlumno().getApellido()
                                    + ", " + text(a.getAsignacionPaciente().getAlumno().getNombre())), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Profesor Autorizante: ", questionFont),
                            new Chunk(text(a.getAsignacionPaciente().getAutorizadoPor().getApellido()
                                    + ", " + text(a.getAsignacionPaciente().getAutorizadoPor().getNombre())), answerFont)));
                    table.addCell(cell(2,
                            new Chunk("Numero de Diagnostico: ", questionFont),
                            new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getId()), answerFont)));
                    if (nullOVacio(a.getAsignacionPaciente().getDiagnostico().getPracticaNoExistente())) {
                        table.addCell(cell(2,
                                new Chunk("Practica Odontologica: ", questionFont),
                                new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getPracticaOdontologica().getDenominacion()), answerFont)));
                    } else {
                        table.addCell(cell(2,
                                new Chunk("Practica Odontologica: ", questionFont),
                                new Chunk(text(a.getAsignacionPaciente().getDiagnostico().getPracticaNoExistente()), answerFont)));
                    }
                    table.addCell(cell(4,
                            new Chunk("Descripción de Procedimiento: ", questionFont),
                            new Chunk(text(a.getDescripcionProcedimiento()), answerFont)));
                    table.addCell(cell(4,
                            new Chunk("Diagnostico Solucionado: ", questionFont),
                            new Chunk(a.isDiagnosticoSolucionado() ? "Si" : "No", answerFont)));
                    par2.add(table);
                }
                par2.add(new Chunk(Chunk.NEWLINE));

                separator(par2, "Historial Medico");
                par2.add(new Chunk(Chunk.NEWLINE));
                document.add(par2);
            } catch (Exception e) {
                throw new RuntimeException("Error al generar Historia Clinica, datos incompletos.", e);
            }

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(100);
            PdfPCell c = new PdfPCell();
            document.add(table2);
            Integer grupo = 0;

            //Detalles Historia Clinica
            try {
                for (DetalleHistoriaClinica d : p.getHistoriaClinica().getDetallesHC()) {
                    if (d.getGrupo() != grupo) {
                        if (grupo != 0) {
                            table2.setSpacingBefore(5f);
                            table2.setSpacingAfter(5f);
                            document.add(table2);
                            table2 = new PdfPTable(2);
                            table2.setWidthPercentage(100);
                        }
                        PdfPCell cell = new PdfPCell();
                        cell.setColspan(2);
                        cell.setBorder(Rectangle.TOP);
                        table2.addCell(cell);
                        grupo = d.getGrupo();
                    }
                    PdfPCell cell = new PdfPCell();
                    if (d instanceof CampoEnumerable) {
                        cell.addElement(this.campoEnumerableDraw(new Paragraph(), d.getNombre(),
                                ((CampoEnumerable) d).getChecked()));
                    }
                    if (d instanceof CampoFecha) {
                        cell.addElement(this.campoFechaDraw(new Paragraph(), d.getNombre(),
                                ((CampoFecha) d).getFecha()));
                    }
                    if (d instanceof CampoDetalle) {
                        cell.addElement(this.campoDetalleDraw(new Paragraph(), d.getNombre(),
                                ((CampoDetalle) d).getOnly_detalle()));
                    }
                    if (d instanceof CampoSiNo) {
                        cell.addElement(this.campoSiNoDraw(new Paragraph(), d.getNombre(),
                                ((CampoSiNo) d).getSiNo()));
                    }
                    cell.setPadding(0);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table2.addCell(cell);
                }

            } catch (Exception e) {
                throw new RuntimeException("Error al generar Historial Medico.");
            }
            document.add(table2);

            document.close();
            System.out.println("PDF Generado con exito!");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }
}
