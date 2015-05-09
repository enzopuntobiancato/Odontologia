package com.utn.tesis.service;

import com.utn.tesis.data.daos.MateriaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
        // TODO: Validar que no exista una materia con el mismo nombre.
        validate(entity, validator);
        return dao.save(entity);
    }

    @Override
    public void update(Materia entity) throws SAPOException {
        validate(entity, validator);
        dao.update(entity);
    }

    public Materia remove(Long materiaId, String motivoBaja) throws SAPOException {
        Materia materia = dao.findById(materiaId);
        materia.darDeBaja(motivoBaja);
        dao.deleteLogical(materia);
        return materia;
    }

    public void restore(Long materiaId) {
        Materia materia = dao.findById(materiaId);
        materia.darDeAlta();
        dao.update(materia);
    }

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
