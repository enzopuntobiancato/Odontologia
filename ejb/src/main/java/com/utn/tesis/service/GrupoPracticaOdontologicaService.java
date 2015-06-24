package com.utn.tesis.service;


import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.DiaHorarioDao;
import com.utn.tesis.data.daos.GrupoPracticaOdontologicaDao;
import com.utn.tesis.model.DiaHorario;
import com.utn.tesis.model.GrupoPracticaOdontologica;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

@Stateless
public class GrupoPracticaOdontologicaService extends BaseService<GrupoPracticaOdontologica> {

    @Inject
    GrupoPracticaOdontologicaDao dao;

    @Inject
    Validator validator;

    @Override
    DaoBase<GrupoPracticaOdontologica> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

}
