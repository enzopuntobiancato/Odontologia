package com.utn.tesis.service;

import com.utn.tesis.data.daos.AsignacionPacienteDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.*;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class AsignacionPacienteService extends BaseService<AsignacionPaciente> {

    @Inject
    AsignacionPacienteDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<AsignacionPaciente> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<AsignacionPaciente> findByFilters(Alumno alumno, Profesor profesor, Diagnostico diagnostico, Calendar fechaCreacion,
                                                  Calendar fechaAsignacion, TrabajoPractico trabajoPractico, Long page, Long pageSize) {
        return dao.findByFilters(alumno, profesor, diagnostico, fechaCreacion, fechaAsignacion, trabajoPractico, page, pageSize);
    }
}