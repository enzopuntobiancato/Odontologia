package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class CatedraService extends BaseService<Catedra> {

    @Inject
    private CatedraDao dao;

    @Inject
    private Validator validator;

    @Inject 
    private DiaHorarioService diaHorarioService;

    @Override
    DaoBase<Catedra> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
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
