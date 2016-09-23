package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.RolDao;
import com.utn.tesis.model.Rol;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 7/07/15
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class RolService extends BaseService<Rol> {

    @Inject
    RolDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Rol> getDao() {
        return this.dao;
    }

    @Override
    Validator getValidator() {
        return this.validator;
    }
}
