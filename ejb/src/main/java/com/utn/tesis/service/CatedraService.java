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
    CatedraDao dao;

    @Inject
    private Validator validator;

    @Inject
    DiaHorarioService diaHorarioService;

    @Override
    DaoBase<Catedra> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Catedra> findByFilters(String denominacion, Long materiaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        return dao.findByFilters(denominacion, materiaId, dadosBaja, pageNumber, pageSize);
    }

    @Override
    public Catedra findById(Long id) {
        Catedra catedra = super.findById(id);
        Collections.reload(catedra, 2);
        return catedra;
    }
}
