package com.utn.tesis.service;

import com.utn.tesis.data.daos.AtencionDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.AsignacionPaciente;
import com.utn.tesis.model.Atencion;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class AtencionService extends BaseService<Atencion> {

    @Inject
    AtencionDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Atencion> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Atencion> findByFilters(Calendar fechaAtencion, AsignacionPaciente asignacionPaciente,
                                        Long page, Long pageSize) {
        return dao.findByFilters(fechaAtencion, asignacionPaciente, page, pageSize);
    }
}
