package com.utn.tesis.service;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.data.daos.PacienteDao;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.exception.SAPOValidationException;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 29/03/16
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PacienteService extends BaseService<Paciente> {
    @Inject
    private PacienteDao dao;
    @Inject
    private Validator validator;
    @Inject
    private PacienteMapper pacienteMapper;
    @Inject
    private HistoriaClinicaService historiaClinicaService;

    @Override
    DaoBase<Paciente> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    public List<Paciente> findByFilters(String nombre, String apellido, Documento documento, Sexo sexo, boolean dadosBaja, Long page, Long pageSize) {
        return dao.findByFilters(nombre, apellido, documento, sexo, dadosBaja, page, pageSize);
    }

    public List<Paciente> findByRol(Rol rol, Long page, Long pageSize) {
        return dao.findByRol(rol, page, pageSize);
    }

    public List<Paciente> findByNombreApellido(String nombApp, Long page, Long pageSize) {
        return dao.findByNombreApellido(nombApp, page, pageSize);
    }

    public PacienteDTO savePaciente(Paciente entity) throws SAPOException {
        HistoriaClinica historiaClinica = historiaClinicaService.save(HistoriaClinica.createDefault());
        entity.setHistoriaClinica(historiaClinica);
        return pacienteMapper.toDTO(save(entity));
    }

    @Override
    protected void bussinessValidation(Paciente entity) throws SAPOValidationException {
        List<Paciente> sameDocumentPersons = dao.validateByDocument(entity);
        if (!sameDocumentPersons.isEmpty()) {
            throw new SAPOValidationException(ImmutableMap.of("documento", "Número y tipo de documento ya registrado."));
        }
        super.bussinessValidation(entity);
    }

    public PacienteDTO findPacienteConDiagnosticos(Long pacienteId) {
        Paciente paciente = this.findById(pacienteId);
        return pacienteMapper.toDTO(paciente);
    }
}
