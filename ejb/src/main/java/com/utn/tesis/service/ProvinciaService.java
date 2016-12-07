package com.utn.tesis.service;


import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.ProvinciaDao;
import com.utn.tesis.mapping.dto.ProvinciaDTO;
import com.utn.tesis.mapping.mapper.ProvinciaMapper;
import com.utn.tesis.model.Provincia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class ProvinciaService extends BaseService<Provincia> {

    @Inject
    private ProvinciaDao dao;
    @Inject
    private Validator validator;
    @Inject
    private ProvinciaMapper provinciaMapper;

    @Override
    DaoBase<Provincia> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Provincia> findByFilters(String nombre, Long page, Long pageSize) {
        return dao.findByFilters(nombre, page, pageSize);
    }

    public List<ProvinciaDTO> findAllOrderByNombre() {
        return provinciaMapper.toDTOList(dao.findAllOrderByNombre());
    }
}
