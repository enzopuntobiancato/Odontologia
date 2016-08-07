package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.TrabajoPracticoDao;
import com.utn.tesis.model.TrabajoPractico;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 13/05/15
 * Time: 13:16
 */
@Stateless
public class TrabajoPracticoService extends BaseService<TrabajoPractico> {

    @Inject
    TrabajoPracticoDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<TrabajoPractico> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<TrabajoPractico> findByFilters(String nombre, Long grupoPracticaId, Long practicaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        return dao.findByFilters(nombre, grupoPracticaId, practicaId, dadosBaja, pageNumber, pageSize);
    }

    public List<TrabajoPractico> findByMateria(Long idMateria){
        return dao.findByMateria(idMateria);
    }
}
