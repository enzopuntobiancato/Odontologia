package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ObraSocialDao;
import com.utn.tesis.model.ObraSocial;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class ObraSocialService extends BaseService<ObraSocial> {

    @Inject
    ObraSocialDao dao;

    @Inject
    Validator validator;

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
}
