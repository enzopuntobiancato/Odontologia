package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PracticaOdontologicaDao;
import com.utn.tesis.model.PracticaOdontologica;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 09/05/15
 * Time: 21:12
 */
@Stateless
public class PracticaOdontologicaService extends BaseService<PracticaOdontologica> {

    @Inject
    private PracticaOdontologicaDao dao;

    @Inject
    private Validator validator;

    @Override
    DaoBase<PracticaOdontologica> getDao() {
        return this.dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<PracticaOdontologica> findByFilters(String denominacion, Long idGrupoPractica, boolean dadosBaja, Long page, Long pageSize) {
        return dao.findByFilters(denominacion, idGrupoPractica, dadosBaja, page, pageSize);
    }

}
