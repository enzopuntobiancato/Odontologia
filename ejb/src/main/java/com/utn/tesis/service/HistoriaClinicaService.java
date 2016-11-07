package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.HistoriaClinicaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.HistoriaClinica;
import com.utn.tesis.model.odontograma.Odontograma;
import com.utn.tesis.model.odontograma.PiezaDental;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Slf4j
public class HistoriaClinicaService extends BaseService<HistoriaClinica> {

    @Inject
    private HistoriaClinicaDao dao;
    @Inject
    private Validator validator;

    @Override
    DaoBase<HistoriaClinica> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    /*public List<HistoriaClinica> findByFilters(Integer numero, Date fechaApertura, Usuario realizoHC,
                                               Atencion atencion, Diagnostico diagnostico, DetalleHistoriaClinica detalleHC,
                                               Long page, Long pageSize) {
        List<HistoriaClinica> historiasClinicas = dao.findByFilters(numero, fechaApertura, realizoHC, atencion, diagnostico,
                detalleHC, page, pageSize);
        return dao.reloadList(historiasClinicas,1);
    }*/

    public HistoriaClinica findByPacienteId(Long pacienteId) {
        HistoriaClinica hc = dao.findByPacienteId(pacienteId);
        return hc;
    }

    @Override
    public HistoriaClinica findById(Long id) {
        HistoriaClinica historiaClinica = dao.findById(id);
        return dao.reload(historiaClinica, 1);
    }

    public boolean createRandomHistoriasClinicas() throws SAPOException {
        List<HistoriaClinica> historiaClinicas = new ArrayList<HistoriaClinica>();

        for (int i = 0; i < 10; i++) {
            HistoriaClinica.createDefault();
            historiaClinicas.add(HistoriaClinica.createDefault());
        }

        try {
            for (HistoriaClinica hc : historiaClinicas) {
                dao.save(hc);
            }
            return true;

        } catch (Exception se) {
            log.error("EXCEPCION: " + se.getMessage());
            return false;
        }
    }

    public boolean saveOdontogramaByPaciente(List<PiezaDental> piezas, Long pacienteId) {
        try {
            HistoriaClinica hc = findByPacienteId(pacienteId);
            Odontograma odontograma = hc.getOdontograma();
            if (piezas != null && piezas.size() > 0) {
                for (PiezaDental pieza : piezas) {
                    odontograma.reemplazarPieza(pieza);
                }
            }
            hc.setOdontograma(odontograma);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean realizarArregloEnPiezasDentales(List<Integer> codigoPiezas,  HistoriaClinica hc) {
        try {
//            HistoriaClinica hc = findByPacienteId(pacienteId);
            Odontograma odontograma = hc.getOdontograma();
            odontograma.realizarArregloEnPiezasDentales(codigoPiezas);
            hc.setOdontograma(odontograma);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}