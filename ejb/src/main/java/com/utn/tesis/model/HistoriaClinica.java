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

    public static final String EMPTY_URI = "data:image/png;base64,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AAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwCBlizMFqEAAECBAgQIECAAAECBAgQIEBgKQEDrKVkrUuAAAECBAgQIECAAAECBAgQIDCLgAHWLIwWIUCAAAECBAgQIECAAAECBAgQWErAAGspWesSIECAAAECBAgQIECAAAECBAjMImCANQujRQgQIECAAAECBAgQIECAAAECBJYSMMBaSta6BAgQIECAAAECBAgQIECAAAECswgYYM3CaBECBAgQIECAAAECBAgQIECAAIGlBAywlpK1LgECBAgQIECAAAECBAgQIECAwCwC/x+maMcqnILLMwAAAABJRU5ErkJggg==";

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
