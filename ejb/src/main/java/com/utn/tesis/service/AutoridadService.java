package com.utn.tesis.service;

import com.utn.tesis.data.daos.AutoridadDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/02/16
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AutoridadService extends BaseService<Autoridad> {

    @Inject
    AutoridadDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Autoridad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Autoridad> findByFilters(String nombre, String apellido, Documento documento,
                                        Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize);
    }

//    public List<Autoridad> findByRol(Rol rol, Long page, Long pageSize) {
//        return dao.findByRol(rol, page, pageSize);
//    }

    public List<Autoridad> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }
}
