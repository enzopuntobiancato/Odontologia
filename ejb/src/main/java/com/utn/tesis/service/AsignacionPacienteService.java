package com.utn.tesis.service;

import com.utn.tesis.data.daos.AsignacionPacienteDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.AsignacionPacienteMapper;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.mapping.mapper.PersonaMapper;
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
    private AsignacionPacienteDao dao;

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private MovimientoAsignacionPacienteService movimientoAsignacionPacienteService;
    @Inject
    private MovimientoDiagnosticoService movimientoDiagnosticoService;
    @Inject
    private PersonaService personaService;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private DiagnosticoService diagnosticoService;
    @Inject
    private PersonaMapper personaMapper;
    @Inject
    private EnumMapper enumMapper;
    @Inject
    private AsignacionPacienteMapper asignacionPacienteMapper;

    @Inject
    private Validator validator;


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

    public AsignacionPacienteDTO save(UsuarioLogueadoDTO usuarioLogueadoDTO, AsignacionPacienteDTO dto) throws SAPOException {
        AsignacionPaciente asignacion;

        if (dto.getId() == null){
            asignacion = asignacionPacienteMapper.fromDTO(dto);
            Usuario usuario = usuarioService.findById(usuarioLogueadoDTO.getId());
            Paciente paciente = pacienteService.findById(dto.getIdPaciente());
            Diagnostico diagnostico = diagnosticoService.findById(dto.getDiagnosticoId());

            MovimientoDiagnostico nuevoMovimientoDiagnostico = new MovimientoDiagnostico
                    (EstadoDiagnostico.RESERVADO, Calendar.getInstance(), usuario);
            movimientoDiagnosticoService.save(nuevoMovimientoDiagnostico);

            diagnostico.addMovimiento(nuevoMovimientoDiagnostico);

            MovimientoAsignacionPaciente nuevoMovimientoAsignacion = new MovimientoAsignacionPaciente
                    (EstadoAsignacionPaciente.PENDIENTE, Calendar.getInstance(), usuario);
            movimientoAsignacionPacienteService.save(nuevoMovimientoAsignacion);

            asignacion.setMovimientoAsignacionPaciente(new ArrayList<MovimientoAsignacionPaciente>());
            asignacion.addMovimientoAsignacionPaciente(nuevoMovimientoAsignacion);
            asignacion.setUltimoMovimiento(nuevoMovimientoAsignacion);
            asignacion.setPaciente(paciente);
            asignacion.setFechaCreacion(Calendar.getInstance());
            asignacion.setDiagnostico(diagnostico);

        }else{
            asignacion = findById(dto.getId());
            asignacionPacienteMapper.updateFromDTO(dto, asignacion);
        }
        save(asignacion);
        return asignacionPacienteMapper.toDTO(asignacion);
    }

    public void cambiarEstadoAsignacion(UsuarioLogueadoDTO usuarioLogueadoDTO,
                                                         AsignacionPacienteDTO dto, String estadoKey) throws SAPOException {
//        AsignacionPaciente asignacion = asignacionPacienteMapper.fromDTO(dto);
        AsignacionPaciente asignacion = dao.findById(dto.getId());
        Usuario usuario = usuarioService.findById(usuarioLogueadoDTO.getId());
        Paciente paciente = pacienteService.findById(dto.getIdPaciente());
        EstadoAsignacionPaciente estado = EstadoAsignacionPaciente.valueOf(estadoKey);
        if(estado == null){
            return;
        }

        MovimientoAsignacionPaciente nuevoMovimientoAsignacion = new MovimientoAsignacionPaciente
                (estado, Calendar.getInstance(), usuario);
//        movimientoAsignacionPacienteService.save(nuevoMovimientoAsignacion);
        asignacion.addMovimientoAsignacionPaciente(nuevoMovimientoAsignacion);
        asignacion.setUltimoMovimiento(nuevoMovimientoAsignacion);
        asignacion.setPaciente(paciente);

        if(estado.equals(EstadoAsignacionPaciente.CANCELADO) || estado.equals(EstadoAsignacionPaciente.ANULADO)){
            MovimientoDiagnostico nuevoMovimientoDiagnostico = new MovimientoDiagnostico
                    (EstadoDiagnostico.PENDIENTE, Calendar.getInstance(), usuario);
            movimientoDiagnosticoService.save(nuevoMovimientoDiagnostico);
            asignacion.getDiagnostico().addMovimiento(nuevoMovimientoDiagnostico);
        }
        if(estado.equals(EstadoAsignacionPaciente.AUTORIZADO)){
            Persona autorizadoPor = usuarioService.findPersonaByUsuario(usuarioLogueadoDTO.getId());
            if(autorizadoPor != null && autorizadoPor instanceof  Profesor){
                asignacion.setAutorizadoPor((Profesor) autorizadoPor);
            }

        }
//        save(asignacion);
    }

    public AsignacionPaciente reload(AsignacionPaciente entity, int depth) {
        return getDao().reload(entity, depth);
    }

    public AsignacionPaciente registrarMovimiento(Long asignacionPacienteId, Long usuarioId, EstadoAsignacionPaciente proximoEstado) throws SAPOException {
        AsignacionPaciente asignacionPaciente = this.findById(asignacionPacienteId);
        Usuario usuario = usuarioService.findById(usuarioId);
        MovimientoAsignacionPaciente ultimoMovimiento = new MovimientoAsignacionPaciente();
        ultimoMovimiento.setFechaMovimiento(Calendar.getInstance());
        ultimoMovimiento.setEstado(proximoEstado);
        ultimoMovimiento.setGeneradoPor(usuario);
        movimientoAsignacionPacienteService.save(ultimoMovimiento);
        asignacionPaciente.setUltimoMovimiento(ultimoMovimiento);
        asignacionPaciente.getMovimientoAsignacionPaciente().add(ultimoMovimiento);
        return asignacionPaciente;
    }
}