package com.utn.tesis.service;

import com.utn.tesis.data.daos.AutoridadDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.Autoridad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/02/16
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AutoridadService extends BaseService<Autoridad> {

    @Inject
    AutoridadDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Autoridad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }
}
