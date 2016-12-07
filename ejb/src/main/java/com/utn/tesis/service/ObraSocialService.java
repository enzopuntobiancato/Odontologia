package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ObraSocialDao;
import com.utn.tesis.model.ObraSocial;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class ObraSocialService extends BaseService<ObraSocial> {

    @Inject
    private ObraSocialDao dao;

    @Inject
    private Validator validator;

    @Override
    DaoBase<ObraSocial> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<ObraSocial> findByFilters(String nombre, Long page, Long pageSize) {
        return dao.findByFilters(nombre, page, pageSize);
    }

    public List<ObraSocial> findAllOrderByNombre() {
        return dao.findAllOrderByNombre();
    }
}
