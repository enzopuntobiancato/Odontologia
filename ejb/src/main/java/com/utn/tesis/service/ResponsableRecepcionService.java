package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ResponsableRecepcionDao;
import com.utn.tesis.model.*;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/02/16
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class ResponsableRecepcionService extends BaseService<ResponsableRecepcion> {

    @Inject
    ResponsableRecepcionDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<ResponsableRecepcion> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<ResponsableRecepcion> findByFilters(String nombre, String apellido, Documento documento,
                                        Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize);
    }

    public List<ResponsableRecepcion> findByRol(Rol rol, Long page, Long pageSize) {
        return dao.findByRol(rol, page, pageSize);
    }

    public List<ResponsableRecepcion> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }
}
