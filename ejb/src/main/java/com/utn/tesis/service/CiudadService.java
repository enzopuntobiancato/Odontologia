package com.utn.tesis.service;

import com.utn.tesis.data.daos.CiudadDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.mapping.dto.CiudadDTO;
import com.utn.tesis.mapping.mapper.CiudadMapper;
import com.utn.tesis.model.Ciudad;
import com.utn.tesis.model.Provincia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class CiudadService extends BaseService<Ciudad> {

    @Inject
    private CiudadDao dao;
    @Inject
    private Validator validator;
    @Inject
    private CiudadMapper ciudadMapper;

    @Override
    DaoBase<Ciudad> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Ciudad> findByFilters(Provincia provincia, String nombre,
                                      Long page, Long pageSize) {
        return dao.findByFilters(provincia, nombre, page, pageSize);
    }

    public List<CiudadDTO> findAllOrderByNombre() {
        return ciudadMapper.toDTOList(dao.findAllOrderByNombre());
    }
}
