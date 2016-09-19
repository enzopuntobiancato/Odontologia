package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.FuncionalidadDao;
import com.utn.tesis.model.Funcionalidad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 17/03/16
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class FuncionalidadService extends BaseService<Funcionalidad> {

    @Inject
    FuncionalidadDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Funcionalidad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

}