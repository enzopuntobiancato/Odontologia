package com.utn.tesis.service;

import com.utn.tesis.data.daos.BarrioDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.mapping.dto.BarrioDTO;
import com.utn.tesis.mapping.mapper.BarrioMapper;
import com.utn.tesis.model.Barrio;
import com.utn.tesis.model.Ciudad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.List;

@Stateless
public class BarrioService extends BaseService<Barrio> {

    @Inject
    private BarrioDao dao;
    @Inject
    private Validator validator;
    @Inject
    private BarrioMapper barrioMapper;

    @Override
    DaoBase<Barrio> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Barrio> findByFilters(Ciudad ciudad, String nombre,
                                      Long page, Long pageSize) {
        return dao.findByFilters(ciudad, nombre, page, pageSize);
    }

    public List<BarrioDTO> findAllOrderByNombre() {
        return barrioMapper.toDTOList(dao.findAllOrderByNombre());
    }
}
