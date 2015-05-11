package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.MateriaDao;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;


@Stateless
public class MateriaService extends BaseService<Materia> {

    @Inject
    private MateriaDao dao;

    @Inject
    private Validator validator;

    @Override
    DaoBase<Materia> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Materia> findByFilters(String nombre, Nivel nivel, boolean dadosBaja, Long page, Long pageSize) {
        return dao.findByFilters(nombre, nivel, dadosBaja, page, pageSize);
    }
}
