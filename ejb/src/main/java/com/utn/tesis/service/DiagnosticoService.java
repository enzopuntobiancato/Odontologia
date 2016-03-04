package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.DiagnosticoDao;
import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.MovimientoDiagnostico;
import com.utn.tesis.model.PracticaOdontologica;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class DiagnosticoService extends BaseService<Diagnostico> {

    @Inject
    DiagnosticoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Diagnostico> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Diagnostico> findByFilters(Calendar fechaCreacion, MovimientoDiagnostico movimiento,
                                           PracticaOdontologica practicaOdontologica, Long page, Long pageSize) {
        return dao.findByFilters(fechaCreacion, movimiento, practicaOdontologica, page, pageSize);
    }
}
