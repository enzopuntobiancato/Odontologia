package com.utn.tesis.service;

import com.utn.tesis.data.daos.MateriaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;


@Stateless
public class MateriaService extends BaseService<Materia> {

    @Inject
    private MateriaDao dao;

    @Inject
    private Validator validator;

    @Override
    public Materia findById(Long idEntity) {
        return dao.findById(idEntity);
    }

    @Override
    public Materia create(Materia entity) throws SAPOException {
        validate(entity, validator);
        return dao.create(entity);
    }

    @Override
    public void update(Materia entity) throws SAPOException {
        validate(entity, validator);
        dao.update(entity);
    }

    @Override
    public void bussinessValidation(Materia entity) throws SAPOValidationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Materia> findByFilters(String nombre, Nivel nivel) {
        return dao.findByFilters(nombre, nivel);
    }

    public List<Materia> findAll() {
          return dao.findAll();
    }
}
