package com.utn.tesis.model;

import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.odontograma.PiezaDental;
import io.github.benas.randombeans.annotation.Exclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "diagnosticos")
public class Diagnostico extends EntityBase {
    private static final long serialVersionUID = -1966730195163804698L;

    @NotNull(message = "La fecha de creacion del diagnostico no puede ser nula.")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Calendar fechaCreacion;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostico_id")
    @NotNull(message = "El movimiento diagnostico no puede ser nulo.")
    private List<MovimientoDiagnostico> movimientos = new ArrayList<MovimientoDiagnostico>();

    @Size(max = 400, message = "La observacion del diagnostico no puede superar los 400 caracteres.")
    @Column(length = 400)
    private String observaciones;

    @Exclude
    @ManyToOne
    @JoinColumn(name = "practica_odontologica_id")
    private PracticaOdontologica practicaOdontologica;

    @Size(max = 50, message = "No puede ingresar más de 50 caracteres")
    @Column(name = "practica_no_existente")
    private String practicaNoExistente;

    @Exclude
    @OneToOne
    @JoinColumn(name = "ultimo_movimiento_diagnostico_id")
    private MovimientoDiagnostico ultimoMovimiento;

    @Exclude
    @ElementCollection
    @CollectionTable(name="piezas", joinColumns=@JoinColumn(name="diagnostico_id"))
    @Column(name="pieza")
    private List<Integer> piezas;


    public Diagnostico() {
    }

    public Diagnostico(Calendar fechaCreacion, String observaciones, PracticaOdontologica practicaOdontologica) {
        this.fechaCreacion = fechaCreacion;
        this.observaciones = observaciones;
        this.practicaOdontologica = practicaOdontologica;
    }

    //GETTERR Y SETTERS

    public List<Integer> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<Integer> piezas) {
        this.piezas = piezas;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<MovimientoDiagnostico> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDiagnostico> movimientos) {
        this.movimientos = movimientos;
    }

    public boolean addMovimiento(MovimientoDiagnostico m) {
        if (movimientos == null || m == null) return false;
        return this.movimientos.add(m);
    }

    public boolean removeMovimiento(MovimientoDiagnostico m) {
        if (movimientos == null || m == null) return false;
        return this.movimientos.add(m);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public PracticaOdontologica getPracticaOdontologica() {
        return practicaOdontologica;
    }

    public void setPracticaOdontologica(PracticaOdontologica practicaOdontologica) {
        this.practicaOdontologica = practicaOdontologica;
    }

    public MovimientoDiagnostico getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(MovimientoDiagnostico ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }

    public String getPracticaNoExistente() {
        return practicaNoExistente;
    }

    public void setPracticaNoExistente(String practicaNoExistente) {
        this.practicaNoExistente = practicaNoExistente;
    }

    @Override
    public void validar() throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
