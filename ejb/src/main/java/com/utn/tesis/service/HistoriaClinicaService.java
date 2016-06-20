package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.HistoriaClinicaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.randomizers.HistoriaClnicaRandomizer;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Slf4j
public class HistoriaClinicaService extends BaseService<HistoriaClinica> {

    @Inject
    HistoriaClinicaDao dao;

    @Inject
    Validator validator;



    @Override
    DaoBase<HistoriaClinica> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<HistoriaClinica> findByFilters(Integer numero, Date fechaApertura, Usuario realizoHC,
                                               Atencion atencion, Diagnostico diagnostico, DetalleHistoriaClinica detalleHC,
                                               Long page, Long pageSize) {
        List<HistoriaClinica> historiasClinicas = dao.findByFilters(numero, fechaApertura, realizoHC, atencion, diagnostico,
                detalleHC, page, pageSize);
        return dao.reloadList(historiasClinicas,1);
    }

    @Override
    public HistoriaClinica findById(Long id){
        HistoriaClinica historiaClinica = dao.findById(id);
        return dao.reload(historiaClinica,1);
    }


    public boolean createRandomHistoriasClinicas() throws SAPOException{
        List<HistoriaClinica> historiaClinicas = new ArrayList<HistoriaClinica>();

        for (int i = 0; i < 10; i++) {
            HistoriaClinica.createDefault();
            historiaClinicas.add(HistoriaClinica.createDefault());
        }

        try{
            for (HistoriaClinica hc : historiaClinicas){
                dao.save(hc);
            }
            return true;

        }catch(Exception se){
            log.error("EXCEPCION: " + se.getMessage());
            return false;
        }
    }


}