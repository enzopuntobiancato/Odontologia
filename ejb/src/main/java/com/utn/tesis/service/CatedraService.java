package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.DiaHorario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class CatedraService extends BaseService<Catedra>{

    @Inject
    CatedraDao dao;

    @Inject
    private Validator validator;

    @Inject DiaHorarioService diaHorarioService;

    @Override
    public Catedra findById(Long idEntity) {
        return dao.findById(idEntity);
    }

    @Override
    public Catedra create(Catedra entity) throws SAPOException {
        List<DiaHorario> horarios = entity.getHorarios();
        for (int i = 0; i < horarios.size(); i++)
        {
            horarios.get(i).setCatedra(entity);
            diaHorarioService.create(horarios.get(i));
        }
        validate(entity, validator);
        return dao.create(entity);
    }

    @Override
    public void update(Catedra entity) throws SAPOException {
        validate(entity, validator);
        dao.update(entity);
    }

    @Override
    public void bussinessValidation(Catedra entity) throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
