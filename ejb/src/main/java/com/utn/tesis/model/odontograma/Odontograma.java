package com.utn.tesis.model.odontograma;

import com.utn.tesis.exception.SAPOException;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Odontograma implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean esAdulto;
    private Calendar fecha;
    private LinkedList<PiezaDental> piezasDentalesSuperiores;
    private LinkedList<PiezaDental> piezasDentalesInferiores;

    public Odontograma() {
        esAdulto = true;
        fecha = Calendar.getInstance();
        piezasDentalesSuperiores = new LinkedList<PiezaDental>();
        piezasDentalesInferiores = new LinkedList<PiezaDental>();
    }

    public static Odontograma createDefault() {
        Odontograma od = new Odontograma();
        od.piezasDentalesSuperiores.addFirst(
                new PiezaDental(Terminology.PD18, Terminology.SUPDER, 1, 8,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD17, Terminology.SUPDER, 1, 7,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD16, Terminology.SUPDER, 1, 6,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD15, Terminology.SUPDER, 1, 5,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD14, Terminology.SUPDER, 1, 4,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD13, Terminology.SUPDER, 1, 3,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD12, Terminology.SUPDER, 1, 2,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD11, Terminology.SUPDER, 1, 1,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.PALATINA,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD21, Terminology.SUPIZQ, 2, 1,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD22, Terminology.SUPIZQ, 2, 2,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD23, Terminology.SUPIZQ, 2, 3,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD24, Terminology.SUPIZQ, 2, 4,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD25, Terminology.SUPIZQ, 2, 5,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD26, Terminology.SUPIZQ, 2, 6,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD27, Terminology.SUPIZQ, 2, 7,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesSuperiores.addLast(
                new PiezaDental(Terminology.PD28, Terminology.SUPIZQ, 2, 8,
                        CaraPiezaDental.createDefault(
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.PALATINA,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addLast(
                new PiezaDental(Terminology.PD38, Terminology.INFIZQ, 3, 8,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD37, Terminology.INFIZQ, 3, 7,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD36, Terminology.INFIZQ, 3, 6,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD35, Terminology.INFIZQ, 3, 5,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD34, Terminology.INFIZQ, 3, 4,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD33, Terminology.INFIZQ, 3, 3,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD32, Terminology.INFIZQ, 3, 2,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD31, Terminology.INFIZQ, 3, 1,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.DISTAL,
                                Terminology.VESTIBULAR,
                                Terminology.MESIAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD41, Terminology.INFDER, 4, 1,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD42, Terminology.INFDER, 4, 2,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD43, Terminology.INFDER, 4, 3,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.INCISAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD44, Terminology.INFDER, 4, 4,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD45, Terminology.INFDER, 4, 5,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD46, Terminology.INFDER, 4, 6,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD47, Terminology.INFDER, 4, 7,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        od.piezasDentalesInferiores.addFirst(
                new PiezaDental(Terminology.PD48, Terminology.INFDER, 4, 8,
                        CaraPiezaDental.createDefault(
                                Terminology.LINGUAL,
                                Terminology.MESIAL,
                                Terminology.VESTIBULAR,
                                Terminology.DISTAL,
                                Terminology.OCLUSAL)));
        return od;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Odontograma)) return false;

        Odontograma that = (Odontograma) o;

        if (!fecha.equals(that.fecha)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fecha.hashCode();
    }

    public boolean isEsAdulto() {
        return esAdulto;
    }

    public void setEsAdulto(boolean esAdulto) {
        this.esAdulto = esAdulto;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public LinkedList<PiezaDental> getPiezasDentalesSuperiores() {
        return piezasDentalesSuperiores;
    }

    public void setPiezasDentalesSuperiores(LinkedList<PiezaDental> piezasDentalesSuperiores) {
        this.piezasDentalesSuperiores = piezasDentalesSuperiores;
    }

    public LinkedList<PiezaDental> getPiezasDentalesInferiores() {
        return piezasDentalesInferiores;
    }

    public void setPiezasDentalesInferiores(LinkedList<PiezaDental> piezasDentalesInferiores) {
        this.piezasDentalesInferiores = piezasDentalesInferiores;
    }

    public void reemplazarPieza(PiezaDental p) {
        if (p == null) return;
        int index = -1;
        if (p.getNumeroSector() == 1 || p.getNumeroSector() == 2) {
            for (int i = 0; i < this.getPiezasDentalesSuperiores().size(); i++) {
                if (this.getPiezasDentalesSuperiores().get(i).getNumeroSector() == p.getNumeroSector() &&
                        this.getPiezasDentalesSuperiores().get(i).getNumeroPieza() == p.getNumeroPieza()) {
                    index = i;
                    break;
                }
            }
            log.info("Updating pieza dental from: {} to {}", this.getPiezasDentalesSuperiores().get(index).toString(), p.toString());
            this.getPiezasDentalesSuperiores().set(index, p);
        } else {
            for (int i = 0; i < this.getPiezasDentalesInferiores().size(); i++) {
                if (this.getPiezasDentalesInferiores().get(i).getNumeroSector() == p.getNumeroSector() &&
                        this.getPiezasDentalesInferiores().get(i).getNumeroPieza() == p.getNumeroPieza()) {
                    index = i;
                    break;
                }
            }
            log.info("Updating pieza dental from: {} to {}", this.getPiezasDentalesInferiores().get(index).toString(), p.toString());
            this.getPiezasDentalesInferiores().set(index, p);
        }
    }

    public void realizarArregloEnPiezasDentales(List<Integer> codigoPiezas) {
        if (codigoPiezas == null || codigoPiezas.size() == 0) {
            return;
        }
        List<PiezaDental> piezasAux = new ArrayList<PiezaDental>();
        for (Integer codigo : codigoPiezas) {
            List<PiezaDental> piezasDentales = codigo <= 28 ? piezasDentalesSuperiores : piezasDentalesInferiores;
            for (PiezaDental piezaDental : piezasDentales) {
                if (piezaDental.getNombrePiezaDental() != codigo) {
                    continue;
                }
                piezasAux.add(piezaDental);
                //Una vez encontrada la pieza, soluciono todos los hallazgos.
                if (piezaDental.getHallazgoClinico() != null) {
                    //Si el diente entero tiene un hallazgo (EXTRACCION, CORONA, TC, PUENTE, ETC), se soluciona y se sale
                    //del bucle.
                    piezaDental.getHallazgoClinico().setEstado(EstadoHallazgoClinico.REALIZADO);
                    break;
                }
                for (CaraPiezaDental cara : piezaDental.getCarasPiezaDental()) {
                    if (cara.getHallazgoClinico() == null) {
                        continue;
                    }
                    cara.getHallazgoClinico().setEstado(EstadoHallazgoClinico.REALIZADO);
                }

            }
        }
        if (piezasAux.size() == 0){
            log.error("NO SE RECONOCIERON LAS PIEZAS A REEMPLAZAR.");
        }
    }
}
