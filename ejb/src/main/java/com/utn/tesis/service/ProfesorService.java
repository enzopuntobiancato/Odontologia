package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ProfesorDao;
import com.utn.tesis.model.Documento;
import com.utn.tesis.model.Profesor;
import com.utn.tesis.model.Sexo;
import com.utn.tesis.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

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

    public List<Profesor> findByFilters(String nombre, String apellido, Documento documento,
                                       Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize);
    }

    public List<Profesor> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }

    public List<Profesor> findProfesoresByApellido(String apellidoNombre, String legajo){
        List<Profesor> profesores = dao.findByNombreApellido(apellidoNombre, null, null);
        profesores = reloadList(profesores, 2);
        return profesores;
    }
}
