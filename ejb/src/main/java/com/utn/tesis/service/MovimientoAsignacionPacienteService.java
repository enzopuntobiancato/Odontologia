package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.MovimientoAsignacionPacienteDao;
import com.utn.tesis.model.EstadoAsignacionPaciente;
import com.utn.tesis.model.MovimientoAsignacionPaciente;
import com.utn.tesis.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class MovimientoAsignacionPacienteService extends BaseService<MovimientoAsignacionPaciente> {

    @Inject
    MovimientoAsignacionPacienteDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<MovimientoAsignacionPaciente> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<MovimientoAsignacionPaciente> findByFilters(EstadoAsignacionPaciente estado, Calendar fechaMovimiento,
                                                            Usuario generadoPor, Long page, Long pageSize) {
        return dao.findByFilters(estado, fechaMovimiento, generadoPor, page, pageSize);
    }
}
