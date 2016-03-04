package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.TrabajoDao;
import com.utn.tesis.model.Trabajo;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class TrabajoService extends BaseService<Trabajo> {

    @Inject
    TrabajoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Trabajo> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Trabajo> findByFilters(String nombre, Long page, Long pageSize) {
        return dao.findByFilters(nombre, page, pageSize);
    }
}
