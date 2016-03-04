package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ResponsableRecepcionDao;
import com.utn.tesis.model.Documento;
import com.utn.tesis.model.ResponsableRecepcion;
import com.utn.tesis.model.Usuario;

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

    public List<ResponsableRecepcion> findByFilters(String nombre, String apellido, Usuario usuario, Documento documento) {
        return dao.findByFilters(nombre, apellido, usuario, documento);
    }
}
