package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.MovimientoDiagnosticoDao;
import com.utn.tesis.model.Atencion;
import com.utn.tesis.model.EstadoDiagnostico;
import com.utn.tesis.model.MovimientoDiagnostico;
import com.utn.tesis.model.Usuario;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class MovimientoDiagnosticoService extends BaseService<MovimientoDiagnostico> {

    @Inject
    MovimientoDiagnosticoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<MovimientoDiagnostico> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<MovimientoDiagnostico> findByFilters(Atencion atencion, EstadoDiagnostico estado, Calendar fechaMovimiento,
                                                     Usuario generadoPor, Long page, Long pageSize) {
        return dao.findByFilters(atencion, estado, fechaMovimiento, generadoPor, page, pageSize);
    }
}
