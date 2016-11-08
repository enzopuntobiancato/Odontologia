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

    @NotNull(message = "El numero de historia clinica no puede ser nulo.")
    private int numero;

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

    //CONSTRUCTORS
    public HistoriaClinica() {
        fechaApertura = Calendar.getInstance();
        atencion = new ArrayList<Atencion>();
        diagnostico = new ArrayList<Diagnostico>();
        detallesHC = new ArrayList<DetalleHistoriaClinica>();
        odontograma = Odontograma.createDefault();
    }

    public HistoriaClinica(int numero, Calendar fechaApertura, Usuario realizoHistoriaClinica,
                           List<Atencion> atencion, List<Diagnostico> diagnostico) {
        this();
        this.numero = numero;
        this.fechaApertura = fechaApertura;
        this.realizoHistoriaClinica = realizoHistoriaClinica;
        this.atencion = atencion;
        this.diagnostico = diagnostico;
    }

    //GETTERS AND SETTERS
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

        return hc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoriaClinica)) return false;
        if (!super.equals(o)) return false;

        HistoriaClinica that = (HistoriaClinica) o;

        if (numero != that.numero) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + numero;
        return result;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
