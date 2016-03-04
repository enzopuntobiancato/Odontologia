package com.utn.tesis.service;

import com.utn.tesis.data.daos.AlumnoDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.Alumno;

import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/02/16
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class AlumnoService extends BaseService<Alumno> {

    @Inject
    AlumnoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Alumno> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }
}
