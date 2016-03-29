package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PacienteDao;
import com.utn.tesis.model.*;

import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 29/03/16
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public class PacienteService extends BaseService<Paciente> {

    @Inject
    PacienteDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<Paciente> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Paciente> findByFilters(String nombre, String apellido, Documento documento,
                                        Usuario usuario, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuario, sexo, page, pageSize);
    }

    public List<Paciente> findByRol(Rol rol, Long page, Long pageSize) {
        return dao.findByRol(rol, page, pageSize);
    }

    public List<Paciente> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }

    /*
    public List<Paciente> findByFilters(Materia mat, TrabajoPractico trabprac, Diagnostico diag, Long page, Long pageSize) {
        return dao.findByFilters(mat, trabprac, diag, page, pageSize);
    }
    */
}
