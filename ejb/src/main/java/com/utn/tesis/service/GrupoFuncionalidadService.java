package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.GrupoFuncionalidadDao;
import com.utn.tesis.model.GrupoFuncionalidad;

import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 17/03/16
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class GrupoFuncionalidadService extends BaseService<GrupoFuncionalidad> {

    @Inject
    GrupoFuncionalidadDao dao;
    @Inject
    Validator validator;

    @Override
    DaoBase<GrupoFuncionalidad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

}