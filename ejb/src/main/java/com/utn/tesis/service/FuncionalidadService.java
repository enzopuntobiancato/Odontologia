package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.FuncionalidadDao;
import com.utn.tesis.mapping.dto.FuncionalidadDTO;
import com.utn.tesis.mapping.mapper.FuncionalidadMapper;
import com.utn.tesis.model.Funcionalidad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class FuncionalidadService extends BaseService<Funcionalidad> {
    @Inject
    private FuncionalidadDao dao;
    @Inject
    private Validator validator;
    @Inject
    private FuncionalidadMapper funcionalidadMapper;

    @Override
    DaoBase<Funcionalidad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<FuncionalidadDTO> findAllFuncionalidades() {
        List<Funcionalidad> entities = dao.findAll();
        return funcionalidadMapper.toDTOList(entities);
    }


}