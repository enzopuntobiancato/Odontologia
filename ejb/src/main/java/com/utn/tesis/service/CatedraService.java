package com.utn.tesis.service;


import com.utn.tesis.data.daos.CatedraDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.CatedraConsultaDTO;
import com.utn.tesis.mapping.dto.CatedraDTO;
import com.utn.tesis.mapping.dto.ProfesorDTO;
import com.utn.tesis.mapping.mapper.CatedraMapper;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.Catedra;
import com.utn.tesis.model.DiaHorario;
import com.utn.tesis.model.Profesor;
import com.utn.tesis.model.TrabajoPractico;
import com.utn.tesis.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Inject
    private PersonaMapper personaMapper;

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
            entity = findById(dto.getId());
            catedraMapper.updateFromDTO(dto, entity);
        }
        entity.setMateria(materiaService.findById(dto.getMateria().getId()));
        List<TrabajoPractico> practicos = new ArrayList<TrabajoPractico>();
        for (TrabajoPractico practico : entity.getTrabajosPracticos()) {
             practicos.add(trabajoPracticoService.findById(practico.getId()));
        }
        entity.setTrabajosPracticos(practicos);
        for (DiaHorario horario: entity.getHorarios()) {
             horario.setCatedra(entity);
        }
        save(entity);
        return catedraMapper.toDTO(entity);
    }

    public List<CatedraConsultaDTO> findCatedrasByProfesor(ProfesorDTO profesorDTO){
        Profesor profesor = personaMapper.profesorFromDTO(profesorDTO);
        List<Catedra> entities = dao.findCatedrasByProfesor(profesor);
        return catedraMapper.toConsultaDTOList(entities);
    }

    public List<CatedraConsultaDTO> findCatedrasByProfesor(Long id){
        List<Catedra> entities = dao.findCatedrasByProfesor(id);
        return catedraMapper.toConsultaDTOList(entities);
    }

    @Override
    protected void bussinessValidation(Catedra entity) throws SAPOValidationException {
        boolean executeNameValidation = true;
        if (!entity.isNew()) {
            Catedra persistedEntity = this.findById(entity.getId());
            executeNameValidation = !entity.getDenominacion().equalsIgnoreCase(persistedEntity.getDenominacion())
                    && entity.getMateria().equals(persistedEntity.getMateria());
        }
        if (executeNameValidation) {
            HashMap<String, Object> filter = new HashMap<String, Object>();
            filter.put("denominacion", entity.getDenominacion());
            filter.put("materia", entity.getMateria());

            List<Catedra> result = dao.findBy(filter);
            if (!result.isEmpty()) {
                HashMap<String, String> error = new HashMap<String, String>(1);
                error.put("denominacion", "La cátedra " + entity.getDenominacion() + " ya se encuentra registrada " +
                        "para la materia " + entity.getMateria().getNombre() + ".");
                throw new SAPOValidationException(error);
            }
        }
        super.bussinessValidation(entity);
    }

}
