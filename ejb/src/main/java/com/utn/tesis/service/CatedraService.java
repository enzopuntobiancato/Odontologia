package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
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
    DaoBase<Catedra> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    @Override
    public Catedra create(Catedra entity) throws SAPOException {
        List<DiaHorario> horarios = entity.getHorarios();
        for (int i = 0; i < horarios.size(); i++)
        {
            horarios.get(i).setCatedra(entity);
            diaHorarioService.create(horarios.get(i));
        }
        return super.create(entity);
    }

}
