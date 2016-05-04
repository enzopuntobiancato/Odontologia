package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.mapping.mapper.CatedraMapper;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.DiaHorario;
import com.utn.tesis.model.TrabajoPractico;
import com.utn.tesis.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CatedraService extends BaseService<Catedra> {
    @Inject
    private CatedraDao dao;
    @Inject
    private Validator validator;
    @Inject
    private DiaHorarioService diaHorarioService;
    @Inject
    private CatedraMapper catedraMapper;
    @Inject
    private MateriaService materiaService;
    @Inject
    private TrabajoPracticoService trabajoPracticoService;

    @Override
    DaoBase<Catedra> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Catedra> findByFilters(String denominacion, Long materiaId, boolean dadosBaja, Long pageNumber, Long pageSize) {
        List<Catedra> result = dao.findByFilters(denominacion, materiaId, dadosBaja, pageNumber, pageSize);
        return result;
    }

    @Override
    public Catedra findById(Long id) {
        Catedra catedra = super.findById(id);
        Collections.reload(catedra, 2);
        return catedra;
    }

    public CatedraDTO save(CatedraDTO dto) throws SAPOException {
        Catedra entity;
        if (dto.getId() == null) {
            entity = catedraMapper.fromDTO(dto);
        } else {
            entity = this.findById(dto.getId());
            catedraMapper.updateFromDTO(dto, entity);
        }
        entity.setMateria(materiaService.findById(entity.getMateria().getId()));
        List<TrabajoPractico> practicos = new ArrayList<TrabajoPractico>();
        for (TrabajoPractico practico : entity.getTrabajosPracticos()) {
             practicos.add(trabajoPracticoService.findById(practico.getId()));
        }
        entity.setTrabajosPracticos(practicos);
        for (DiaHorario horario: entity.getHorarios()) {
             horario.setCatedra(entity);
        }
        this.save(entity);
        return catedraMapper.toDTO(entity);
    }


}
