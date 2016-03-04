package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ProfesorDao;
import com.utn.tesis.model.Documento;
import com.utn.tesis.model.Profesor;
import com.utn.tesis.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/02/16
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class ProfesorService extends BaseService<Profesor> {

    @Inject
    ProfesorDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Profesor> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Profesor> findByFilters(String nombre, String apellido, Integer legajo, Usuario usuario, Documento documento) {
        return dao.findByFilters(nombre, apellido, legajo, usuario, documento);
    }
}
