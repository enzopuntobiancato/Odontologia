package com.utn.tesis.service;


import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.DiaHorarioDao;
import com.utn.tesis.data.daos.GrupoPracticaOdontologicaDao;
import com.utn.tesis.mapping.dto.GrupoPracticaOdontologicaDTO;
import com.utn.tesis.mapping.mapper.GrupoPracticaOdontologicaMapper;
import com.utn.tesis.model.DiaHorario;
import com.utn.tesis.model.GrupoPracticaOdontologica;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class GrupoPracticaOdontologicaService extends BaseService<GrupoPracticaOdontologica> {

    @Inject
    private GrupoPracticaOdontologicaDao dao;
    @Inject
    private Validator validator;
    @Inject
    private GrupoPracticaOdontologicaMapper grupoPracticaMapper;

    @Override
    DaoBase<GrupoPracticaOdontologica> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<GrupoPracticaOdontologicaDTO> findAllGruposOrderByNombre() {
        return grupoPracticaMapper.toDTOList(dao.findAllOrderByNombre());
    }

}
