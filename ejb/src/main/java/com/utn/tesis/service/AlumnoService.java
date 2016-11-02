package com.utn.tesis.service;

import com.utn.tesis.data.daos.AlumnoDao;
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
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AlumnoService extends BaseService<Alumno> {

    @Inject
    AlumnoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Alumno> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Alumno> findByFilters(String nombre, String apellido, Documento documento,
                                        Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize);
    }

//    public List<Alumno> findByRol(Rol rol, Long page, Long pageSize) {
//        return dao.findByRol(rol, page, pageSize);
//    }

    public List<Alumno> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }
}
