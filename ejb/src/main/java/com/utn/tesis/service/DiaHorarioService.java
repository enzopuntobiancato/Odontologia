package com.utn.tesis.service;


import com.utn.tesis.data.daos.DiaHorarioDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
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
    public DiaHorario findById(Long idEntity) {
        return dao.findById(idEntity);
    }

    @Override
    public DiaHorario create(DiaHorario entity) throws SAPOException {
        validate(entity, validator);
        return dao.create(entity);
    }

    @Override
    public void update(DiaHorario entity) throws SAPOException {
        validate(entity, validator);
        dao.update(entity);
    }

    @Override
    public void bussinessValidation(DiaHorario entity) throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
