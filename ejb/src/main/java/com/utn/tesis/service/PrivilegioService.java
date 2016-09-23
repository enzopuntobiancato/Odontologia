package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PrivilegioDao;
import com.utn.tesis.model.Privilegio;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 17/03/16
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PrivilegioService extends BaseService<Privilegio> {

    @Inject
    PrivilegioDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Privilegio> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }
}
