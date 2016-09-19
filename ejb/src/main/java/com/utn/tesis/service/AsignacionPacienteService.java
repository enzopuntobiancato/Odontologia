package com.utn.tesis.service;

import com.utn.tesis.data.daos.AsignacionPacienteDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.AsignacionPacienteDTO;
import com.utn.tesis.mapping.dto.AsignacionPacienteEditCreateDTO;
import com.utn.tesis.mapping.dto.DiagnosticoSupport;
import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.mapper.AsignacionPacienteMapper;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.model.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Validator;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 04/03/16
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AsignacionPacienteService extends BaseService<AsignacionPaciente> {

    @Inject
    AsignacionPacienteDao dao;
    @Inject
    EnumMapper enumMapper;
    @Inject
    AsignacionPacienteMapper asignacionPacienteMapper;
    @Inject
    Validator validator;
    @Inject
    PacienteService pacienteService;

    @Override
    DaoBase<AsignacionPaciente> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<AsignacionPaciente> findByFilters(Long alumnoId,String nombreAlumno,String apellidoAlumno,
                                                  Documento documentoAlumno,
                                                  Long profesorId, String nombrePaciente,
                                                  String apellidoPaciente, Documento documentoPaciente,
                                                  Long catedraId, EstadoAsignacionPaciente estado,
                                                  Long diagnosticoId, Calendar fechaCreacion,
                                                  Calendar fechaAsignacion, Long trabajoPracticoId, boolean dadosBaja,
                                                  Long page, Long pageSize) {

        List<AsignacionPaciente> entities = dao.findByFilters(alumnoId, nombreAlumno, apellidoAlumno,
                documentoAlumno, profesorId, nombrePaciente, apellidoPaciente,
                documentoPaciente, catedraId, estado, diagnosticoId, fechaCreacion, fechaAsignacion, trabajoPracticoId,
                dadosBaja, page, pageSize);
        return entities;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<AsignacionPaciente> findAsignacionesConfirmadasAutorizadas(Long alumnoId,String nombreAlumno,String apellidoAlumno,
                                                  Documento documentoAlumno,
                                                  Long profesorId, String nombrePaciente,
                                                  String apellidoPaciente, Documento documentoPaciente,
                                                  Long catedraId, Long trabajoPracticoId,
                                                  Long page, Long pageSize) {

        List<AsignacionPaciente> entities = dao.findAsignacionesConfirmadasAutorizadas(alumnoId, nombreAlumno, apellidoAlumno,
                documentoAlumno, profesorId, nombrePaciente,apellidoPaciente, documentoPaciente, trabajoPracticoId,
                catedraId, page, pageSize);
        return entities;
    }

    public List<DiagnosticoSupport> getDiagnosticosSupport(Documento documentoPaciente,
                                                           Long catedraId, Long trabajoPracticoId,
                                                           Calendar fechaAsignacion,
                                                           Long page, Long pageSize) {
        return dao.getDiagnosticosSupport(documentoPaciente, catedraId, trabajoPracticoId, fechaAsignacion, page, pageSize);
    }

    public boolean estaAutorizado(Long alumnoId, Long trabajoPracticoId) {
        return dao.estaAutorizado(alumnoId, trabajoPracticoId);
    }

    public List<EnumDTO> getEstadosAsignaciones() {
        return enumMapper.estadoAsignacionListToDTOList(Arrays.asList(EstadoAsignacionPaciente.values()));
    }


    public AsignacionPacienteDTO save(AsignacionPacienteDTO dto) throws SAPOException {
        AsignacionPaciente entity;
        Paciente paciente = pacienteService.findById(dto.getIdPaciente());
        if (dto.getId() == null) {
            entity = asignacionPacienteMapper.fromDTO(dto);
            List<MovimientoAsignacionPaciente> movimientos = new ArrayList<MovimientoAsignacionPaciente>();
            MovimientoAsignacionPaciente primerMovimiento = new MovimientoAsignacionPaciente(EstadoAsignacionPaciente.PENDIENTE, Calendar.getInstance(), null);
            movimientos.add(primerMovimiento);
            entity.setFechaCreacion(Calendar.getInstance());
            entity.setMovimientoAsignacionPaciente(movimientos);
            entity.setUltimoMovimiento(primerMovimiento);
        } else {
            entity = findById(dto.getId());
            asignacionPacienteMapper.updateFromDTO(dto, entity);
        }
        entity.setPaciente(paciente);
        save(entity);
        return asignacionPacienteMapper.toDTO(entity);
    }

    public AsignacionPaciente reload(AsignacionPaciente entity, int depth) {
        return getDao().reload(entity, depth);
    }

    @Override
    public AsignacionPaciente remove(Long id, String motivoBaja) throws SAPOException {
        AsignacionPaciente entity = getDao().findById(id);
        List<MovimientoAsignacionPaciente> movimientos = entity.getMovimientoAsignacionPaciente();
        MovimientoAsignacionPaciente nuevoMovimiento = new MovimientoAsignacionPaciente(EstadoAsignacionPaciente.CANCELADO, Calendar.getInstance(), null);
        movimientos.add(nuevoMovimiento);
        entity.setUltimoMovimiento(nuevoMovimiento);
        entity.darDeBaja(motivoBaja);
        getDao().deleteLogical(entity);
        return entity;
    }

    @Override
    public void restore(Long id) {
        AsignacionPaciente entity = getDao().findById(id);
        entity.darDeAlta();
        List<MovimientoAsignacionPaciente> movimientos = entity.getMovimientoAsignacionPaciente();
        MovimientoAsignacionPaciente nuevoMovimiento = new MovimientoAsignacionPaciente(EstadoAsignacionPaciente.PENDIENTE, Calendar.getInstance(), null);
        movimientos.add(nuevoMovimiento);
        entity.setUltimoMovimiento(nuevoMovimiento);
        getDao().update(entity);
    }
}