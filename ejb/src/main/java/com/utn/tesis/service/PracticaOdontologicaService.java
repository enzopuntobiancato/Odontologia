package com.utn.tesis.service;

import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PracticaOdontologicaDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.PracticaOdontologicaDTO;
import com.utn.tesis.mapping.mapper.PracticaOdontologicaMapper;
import com.utn.tesis.model.PracticaOdontologica;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Collections;
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
    private GrupoPracticaOdontologicaService grupoPracticaOdontologicaService;
    @Inject
    private PracticaOdontologicaMapper practicaMapper;

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

    public PracticaOdontologicaDTO save(PracticaOdontologicaDTO dto) throws SAPOException {
        PracticaOdontologica entity;
        if (dto.getId() == null) {
            entity = practicaMapper.fromDTO(dto);
        } else {
            entity = this.findById(dto.getId());
            practicaMapper.updateFromDTO(dto, entity);
        }
        entity.setGrupo(grupoPracticaOdontologicaService.findById(dto.getGrupo().getId()));

        return practicaMapper.toDTO(this.save(entity));
    }

    public List<PracticaOdontologicaDTO> findByDenominacion(String denominacion) {
        if (StringUtils.isBlank(denominacion)) {
            return Collections.emptyList();
        }
        return practicaMapper.toDTOList(dao.findByDenominacion(denominacion));
    }

    public List<PracticaOdontologicaDTO> findByDenominacionCatedraAndPractico(String denominacion, Long catedraId, Long trabajoPracticoId) {
        return practicaMapper.toDTOList(dao.findByDenominacionCatedraAndPractico(denominacion, catedraId, trabajoPracticoId));
    }

    @Override
    protected void bussinessValidation(PracticaOdontologica entity) throws SAPOValidationException {
        boolean executeNameValidation = true;
        if (!entity.isNew()) {
            PracticaOdontologica persistedEntity = this.findById(entity.getId());
            executeNameValidation = !(entity.getDenominacion().equalsIgnoreCase(persistedEntity.getDenominacion())
                    && entity.getGrupo().equals(persistedEntity.getGrupo()));
        }
        if (executeNameValidation) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("denominacion", entity.getDenominacion());
            filter.put("grupo", entity.getGrupo());

            List<PracticaOdontologica> result = dao.findBy(filter);
            if (!result.isEmpty()) {
                HashMap<String, String> error = new HashMap<String, String>(1);
                error.put("denominacion", "La práctica " + entity.getDenominacion() + " ya se encuentra registrada " +
                        "para el grupo " + entity.getGrupo().getNombre() + ".");
                throw new SAPOValidationException(error);
            }
        }
        super.bussinessValidation(entity);
    }
}
