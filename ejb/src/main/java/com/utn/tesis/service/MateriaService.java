package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.MateriaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.MateriaDTO;
import com.utn.tesis.mapping.mapper.MateriaMapper;
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

    @Inject
    private MateriaMapper materiaMapper;

    @Override
    DaoBase<Materia> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<MateriaDTO> findByFilters(String nombre, Nivel nivel, boolean dadosBaja, Long page, Long pageSize) {
        List<Materia> result = dao.findByFilters(nombre, nivel, dadosBaja, page, pageSize);
        return materiaMapper.toDTOList(result);
    }

    @Override
    protected void bussinessValidation(Materia entity) throws SAPOValidationException {
        boolean executeNameValidation = true;
        if (!entity.isNew()) {
            Materia persistedEntity = this.findById(entity.getId());
            executeNameValidation = !entity.getNombre().equalsIgnoreCase(persistedEntity.getNombre());
        }
        if (executeNameValidation) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("nombre", entity.getNombre());
            filter.put("fechaBaja", null);
            List<Materia> result = dao.findBy(filter);
            if (!result.isEmpty()) {
                HashMap<String, String> error = new HashMap<String, String>(1);
                error.put("nombre", "El nombre " + entity.getNombre() + " ya se encuentra registrado.");
                throw new SAPOValidationException(error);
            }
        }
        super.bussinessValidation(entity);
    }
}
