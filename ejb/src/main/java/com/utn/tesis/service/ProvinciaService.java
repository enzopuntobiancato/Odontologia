package com.utn.tesis.service;


import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ProvinciaDao;
import com.utn.tesis.model.Provincia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class ProvinciaService extends BaseService<Provincia> {

    @Inject
    ProvinciaDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Provincia> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Provincia> findByFilters(String nombre, Long page, Long pageSize) {
        return dao.findByFilters(nombre, page, pageSize);
    }

}
