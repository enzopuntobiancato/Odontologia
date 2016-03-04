package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.DomicilioDao;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Domicilio;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 20/02/16
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class DomicilioService extends BaseService<Domicilio> {

    @Inject
    DomicilioDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Domicilio> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Domicilio> findByFilters(Barrio barrio, String calle, String numero, Integer codigoPostal,
                                         Long page, Long pageSize) {
        return dao.findByFilters(barrio, calle, numero, codigoPostal, page, pageSize);
    }
}
