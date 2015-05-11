package com.utn.tesis.service;


import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.DiaHorarioDao;
import com.utn.tesis.model.DiaHorario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

@Stateless
public class DiaHorarioService extends BaseService<DiaHorario> {

    @Inject
    DiaHorarioDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<DiaHorario> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

}
