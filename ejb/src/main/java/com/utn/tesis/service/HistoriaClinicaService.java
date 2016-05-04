package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.HistoriaClinicaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.model.*;

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

        return dao.findByFilters(numero, fechaApertura, realizoHC, atencion, diagnostico, detalleHC, page, pageSize);
    }


}