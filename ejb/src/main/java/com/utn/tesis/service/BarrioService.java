package com.utn.tesis.service;

import com.utn.tesis.data.daos.BarrioDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class BarrioService extends BaseService<Barrio> {

    @Inject
    BarrioDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Barrio> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Barrio> findByFilters(Ciudad ciudad, String nombre,
                                      Long page, Long pageSize) {
        return dao.findByFilters(ciudad, nombre, page, pageSize);
    }

}
