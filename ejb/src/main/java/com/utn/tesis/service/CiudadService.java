package com.utn.tesis.service;

import com.utn.tesis.data.daos.CiudadDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.Provincia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class CiudadService extends BaseService<Ciudad> {

    @Inject
    CiudadDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Ciudad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Ciudad> findByFilters(Provincia provincia, String nombre,
                                      Long page, Long pageSize) {
        return dao.findByFilters(provincia, nombre, page, pageSize);
    }
}
