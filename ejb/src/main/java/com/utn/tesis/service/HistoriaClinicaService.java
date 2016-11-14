package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.HistoriaClinicaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.*;
import com.utn.tesis.randomizers.HistoriaClnicaRandomizer;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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