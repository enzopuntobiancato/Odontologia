package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.odontograma.Odontograma;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.usertype.DynamicParameterizedType;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "historias_clinicas")
public class HistoriaClinica extends EntityBase {
    private static final long serialVersionUID = -8797692234912388646L;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de apertura de historia clinica no puede ser nula.")
    @Column(name = "fecha_apertura")
    private Calendar fechaApertura;

    //se tiene que definir la persona que va  aca.
    @ManyToOne
    @JoinColumn(name = "usuario_realizo_hc_id")
    private Usuario realizoHistoriaClinica;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "historia_clinica_id")
    private List<Atencion> atencion;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "historia_clinica_id")
    private List<Diagnostico> diagnostico;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "historia_clinica_id")
    private List<Archivo> documentacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Error en generacion de historia clinica. Imposible guardar historia clinica vacia.")
    @JoinColumn(name = "historia_clinica_id")
    private List<DetalleHistoriaClinica> detallesHC;

    @Type(type = "json", parameters = @Parameter(name = DynamicParameterizedType.RETURNED_CLASS, value = "com.utn.tesis.model.odontograma.Odontograma"))
    @Column(columnDefinition = "TEXT")
    private Odontograma odontograma;

    @Column(columnDefinition = "TEXT")
    private String odontogramaUri;

    public static final String EMPTY_URI = "data:image/png;base64,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AgQI7ErAAGtXsh6XAAECBAgQIECAAAECBAgQIEBgKwIGWFth9CAECBAgQIAAAQIECBAgQIAAAQK7EjDA2pWsxyVAgAABAgQIECBAgAABAgQIENiKgAHWVhg9CAECBAgQIECAAAECBAgQIECAwK4EDLB2JetxCRAgQIAAAQIECBAgQIAAAQIEtiJggLUVRg9CgAABAgQIECBAgAABAgQIECCwKwEDrF3JelwCBAgQIECAAAECBAgQIECAAIGtCBhgbYXRgxAgQIAAAQIECBAgQIAAAQIECOxKwABrV7IelwABAgQIECBAgAABAgQIECBAYCsCBlhbYfQgBAgQIECAAAECBAgQIECAAAECuxIwwNqVrMclQIAAAQIECBAgQIAAAQIECBDYioAB1lYYPQgBAgQIECBAgAABAgQIECBAgMCuBAywdiXrcQkQIECAAAECBAgQIECAAAECBLYiYIC1FUYPQoAAAQIECBAgQIAAAQIECBAgsCsBA6xdyXpcAgQIECBAgAABAgQIECBAgACBrQgYYG2F0YMQIECAAAECBAgQIECAAAECBAjsSsAAa1eyHpcAAQIECBAgQIAAAQIECBAgQGArAgZYW2H0IAQIECBAgAABAgQIECBAgAABArsSMMDalazHJUCAAAECBAgQIECAAAECBAgQ2IqAAdZWGD0IAQIECBAgQIAAAQIECBAgQIDArgQMsHYl63EJECBAgAABAgQIECBAgAABAgS2ImCAtRVGD0KAAAECBAgQIECAAAECBAgQILArAQOsXcl6XAIECBAgQIAAAQIECBAgQIAAga0IGGBthdGDECBAgAABAgQIECBAgAABAgQI7ErAAGtXsh6XAAECBAgQIECAAAECBAgQIEBgKwL/H263xsZcgsjjAAAAAElFTkSuQmCC";

    //CONSTRUCTORS
    public HistoriaClinica() {
        fechaApertura = Calendar.getInstance();
        atencion = new ArrayList<Atencion>();
        diagnostico = new ArrayList<Diagnostico>();
        detallesHC = new ArrayList<DetalleHistoriaClinica>();
        odontograma = Odontograma.createDefault();
    }

    public HistoriaClinica(Calendar fechaApertura, Usuario realizoHistoriaClinica,
                           List<Atencion> atencion, List<Diagnostico> diagnostico) {
        this();
        this.fechaApertura = fechaApertura;
        this.realizoHistoriaClinica = realizoHistoriaClinica;
        this.atencion = atencion;
        this.diagnostico = diagnostico;
    }

    public String getOdontogramaUri() {
        return odontogramaUri;
    }

    public void setOdontogramaUri(String odontogramaUri) {
        this.odontogramaUri = odontogramaUri;
    }

    public Calendar getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Calendar fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Usuario getRealizoHistoriaClinica() {
        return realizoHistoriaClinica;
    }

    public void setRealizoHistoriaClinica(Usuario realizoHistoriaClinica) {
        this.realizoHistoriaClinica = realizoHistoriaClinica;
    }

    public List<Atencion> getAtencion() {
        return atencion;
    }

    public void setAtencion(List<Atencion> atencion) {
        this.atencion = atencion;
    }

    public List<Diagnostico> getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(List<Diagnostico> diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<DetalleHistoriaClinica> getDetallesHC() {
        return detallesHC;
    }

    public void setDetallesHC(List<DetalleHistoriaClinica> detallesHC) {
        this.detallesHC = detallesHC;
    }

    public boolean addAtencion(Atencion a) {
        if (a == null || atencion == null) {
            return false;
        }
        return atencion.add(a);
    }

    public boolean removeAtencion(Atencion a) {
        if (a == null || atencion == null) {
            return false;
        }
        return atencion.remove(a);
    }

    public void clearAtenciones() {
        if (atencion != null) {
            atencion.clear();
        }
    }

    public boolean addDiagnostico(Diagnostico d) {
        if (d == null || diagnostico == null) {
            return false;
        }
        return diagnostico.add(d);
    }

    public boolean updateDiagnostico(Diagnostico d) {
        if (d == null || diagnostico == null) return false;
        int index = diagnostico.indexOf(d);
        if (index != -1) {
            diagnostico.set(index, d);
            return true;
        }
        return false;
    }

    public boolean removeDiagnostico(Diagnostico d) {
        if (d == null || diagnostico == null) {
            return false;
        }
        return diagnostico.remove(d);
    }

    public void clearDiagnosticos() {
        if (diagnostico != null) {
            diagnostico.clear();
        }
    }

    private void addDetalle(DetalleHistoriaClinica dhc) {
        if (dhc == null) {
            return;
        }
        detallesHC.add(dhc);
    }

    public List<Archivo> getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(List<Archivo> documentacion) {
        this.documentacion = documentacion;
    }

    public Odontograma getOdontograma() {
        return odontograma;
    }

    public void setOdontograma(Odontograma odontograma) {
        this.odontograma = odontograma;
    }

    public static HistoriaClinica createDefault() {
        HistoriaClinica hc = new HistoriaClinica();
        hc.setOdontograma(Odontograma.createDefault());

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G1P1, 1, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G1P2, 1, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G1P3, 1, 3));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G2P1, 2, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G2P2, 2, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G2P3, 2, 3));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G2P4, 2, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P1, 3, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P2, 3, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P3, 3, 3));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G3P4, 3, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P5, 3, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P6, 3, 6));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P7, 3, 7));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P8, 3, 8));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P9, 3, 9));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G3P10, 3, 10));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P11, 3, 11));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G4P1, 4, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G4P2, 4, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G4P3, 4, 3));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G4P4, 4, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P1, 5, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P2, 5, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G5P3, 5, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P4, 5, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P5, 5, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P6, 5, 6));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G5P7, 5, 7));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P8, 5, 8));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G5P9, 5, 9));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P1, 6, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P2, 6, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P3, 6, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G6P4, 6, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G7P1, 7, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G7P2, 7, 2));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G8P1, 8, 1));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P2, 8, 2));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P3, 8, 3));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P4, 8, 4));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P5, 8, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G8P6, 8, 6));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G9P1, 9, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P2, 9, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P3, 9, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P4, 9, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P1, 10, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P2, 10, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G10P3, 10, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P4, 10, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P5, 10, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P6, 10, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P7, 10, 7));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P8, 10, 8));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P9, 10, 9));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G11P1, 11, 1));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G11P2, 11, 2));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G11P3, 11, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G11P4, 11, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G11P5, 11, 5));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G11P6, 11, 6));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G12P1, 12, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G12P2, 12, 2));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G13P1, 13, 1));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G13P2, 13, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G13P3, 13, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G13P4, 13, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G14P1, 14, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G14P2, 14, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G14P3, 14, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G14P4, 14, 4));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P1, 15, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P2, 15, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P3, 15, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P4, 15, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P5, 15, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P6, 15, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P7, 15, 7));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G15P8, 15, 8));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P9, 15, 9));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P10, 15, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P11, 15, 11));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G15P12, 15, 12));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P13, 15, 13));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G16P1, 16, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G16P2, 16, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G16P3, 16, 3));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G17P1, 17, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P2, 17, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P3, 17, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P4, 17, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G17P5, 17, 5));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G17P6, 17, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P7, 17, 7));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G18P1, 18, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G18P2, 18, 2));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P3, 18, 3));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P4, 18, 4));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P5, 18, 5));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P1, 19, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P2, 19, 2));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G19P3, 19, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P4, 19, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P5, 19, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P6, 19, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P7, 19, 7));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P8, 19, 8));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P9, 19, 9));

        //Preguntas para mujeres
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P1, 20, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P2, 20, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P3, 20, 3));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G20P4, 20, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P5, 20, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P6, 20, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P7, 20, 7));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P8, 20, 8));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P9, 20, 9));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P10, 20, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P11, 20, 11));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P12, 20, 12));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P13, 20, 13));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P14, 20, 14));
        //Fin de preguntas para mujeres

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P1, 21, 1));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G21P2, 21, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P3, 21, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P4, 21, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P5, 21, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P6, 21, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P7, 21, 7));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P8, 21, 8));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P9, 21, 9));
        hc.addDetalle(new CampoFecha(DetalleHistoriaClinica.G21P10, 21, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P11, 21, 11));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P12, 21, 12));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P13, 21, 13));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G22P1, 22, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P2, 22, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G22P3, 22, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P4, 22, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P5, 22, 5));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G23P1, 23, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G23P2, 23, 2));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G24P1, 24, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G24P2, 24, 2));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G25P1, 25, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G25P2, 25, 2));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P3, 25, 3));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P4, 25, 4));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P5, 25, 5));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G26P1, 26, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G26P2, 26, 2));

        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P1, 27, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G27P2, 27, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P3, 27, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P4, 27, 4));

        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P1, 28, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P2, 28, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G28P3, 28, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P4, 28, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G28P5, 28, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P6, 28, 6));

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G29P1, 29, 1));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G29P2, 29, 2));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G29P3, 29, 3));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G29P4, 29, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G29P5, 29, 5));

        hc.setOdontogramaUri(EMPTY_URI);

        return hc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoriaClinica)) return false;
        if (!super.equals(o)) return false;

        HistoriaClinica that = (HistoriaClinica) o;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
