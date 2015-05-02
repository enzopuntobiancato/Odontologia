package com.utn.tesis.service;

import com.utn.tesis.data.daos.MateriaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
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
        return dao.save(entity);
    }

    @Override
    public void update(Materia entity) throws SAPOException {
        validate(entity, validator);
        dao.update(entity);
    }

    public void remove(Long materiaId, String motivoBaja) {
        Materia materia = dao.findById(materiaId);
        materia.darDeBaja(motivoBaja);
        dao.deleteLogical(materia);
    }

    public void restore(Long materiaId) {
        Materia materia = dao.findById(materiaId);
        materia.darDeAlta();
        dao.update(materia);
    }

//    @Override
//    public void bussinessValidation(Materia entity) throws SAPOValidationException {
//        //To change body of implemented methods use File | Settings | File Templates.
////        HashMap<String, String> vals = new HashMap<String, String>();
////        vals.put("regla1", "No se cumplio la regla 1");
////        SAPOValidationException ex = new SAPOValidationException(vals);
////        throw ex;
//
//    }

    public List<Materia> findByFilters(String nombre, Nivel nivel, boolean dadosBaja, Long page, Long pageSize) {
        return dao.findByFilters(nombre, nivel, dadosBaja, page, pageSize);
    }


    public List<Materia> findAll() {
          return dao.findAll();
    }

    public List<Materia> findBySpecs(String name, Nivel nivel) {
        return null;
    }
}
