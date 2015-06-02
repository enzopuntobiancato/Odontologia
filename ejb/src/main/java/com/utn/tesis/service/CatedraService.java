package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.DiaHorario;
import com.utn.tesis.util.Collections;

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

    public List<Catedra> findByFilters(String denominacion, Long materiaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        List<Catedra> result = dao.findByFilters(denominacion, materiaId, dadosBaja, pageNumber, pageSize);
         return result;
    }

    @Override
    public Catedra findById(Long id) {
        Catedra catedra = super.findById(id);
        Collections.reload(catedra, 2);
        return catedra;
    }
}
