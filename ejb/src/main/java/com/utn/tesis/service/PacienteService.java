package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PacienteDao;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 29/03/16
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
@Stateless
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
                                        String usuarioNombre, Sexo sexo, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, usuarioNombre, sexo, page, pageSize);
    }

    public List<Paciente> findByRol(Rol rol, Long page, Long pageSize) {
        return dao.findByRol(rol, page, pageSize);
    }

    public List<Paciente> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }

    @Override
    protected void bussinessValidation(Paciente entity) throws SAPOValidationException {

        boolean executeNameValidation = true;
        if(!entity.isNew()){
            Paciente persistedEntity = findById(entity.getId());
            executeNameValidation = !entity.equals(persistedEntity);
        }
        if(executeNameValidation){
            HashMap<String,Object> filter = new HashMap<String, Object>();

           filter.put("documento",entity.getDocumento());
           List<Paciente> result = dao.findBy(filter);
            if(!result.isEmpty()){
                HashMap<String,String> error = new HashMap<String, String>(1);
                error.put("nombre","El paciente con documento " + entity.getDocumento().toString() + " ya está registrado");
                throw new SAPOValidationException(error);
            }
        }
       super.bussinessValidation(entity);
    }
}
