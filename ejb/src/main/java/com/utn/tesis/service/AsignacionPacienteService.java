package com.utn.tesis.service;

import com.utn.tesis.data.daos.AsignacionPacienteDao;
import com.utn.tesis.data.daos.DaoBase;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mail.MailService;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.AsignacionPacienteMapper;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.util.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private MailService mailService;
    @Inject
    private FileUtils fileUtils;
    @Inject
    private PdfGenerator pdfGenerator;

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
    public List<AsignacionPacienteDTO> findByFilters(Long alumnoId,
                                                     String nombreAlumno,
                                                     String apellidoAlumno,
                                                     TipoDocumento tipoDocumentoAlumno,
                                                     String numeroDocumentoAlumno,
                                                     Long profesorId,
                                                     boolean isProfe,
                                                     List<Long> catedrasProfe,
                                                     String nombrePaciente,
                                                     String apellidoPaciente,
                                                     TipoDocumento tipoDocumentoPaciente,
                                                     String numeroDocumentoPaciente,
                                                     Long catedraId,
                                                     EstadoAsignacionPaciente estado,
                                                     Long diagnosticoId,
                                                     Calendar fechaCreacion,
                                                     Calendar fechaAsignacion,
                                                     Long trabajoPracticoId,
                                                     Long page,
                                                     Long pageSize) {

        List<AsignacionPaciente> entities = dao.findByFilters(alumnoId,
                nombreAlumno,
                apellidoAlumno,
                tipoDocumentoAlumno,
                numeroDocumentoAlumno,
                profesorId,
                isProfe,
                catedrasProfe,
                nombrePaciente,
                apellidoPaciente,
                tipoDocumentoPaciente,
                numeroDocumentoPaciente,
                catedraId,
                estado,
                diagnosticoId,
                fechaCreacion,
                fechaAsignacion,
                trabajoPracticoId,
                page,
                pageSize);
        return asignacionPacienteMapper.toDTOList(entities);
    }

    public List<AsignacionPacienteDTO> findAsignacionesConfirmadas(String nombreAlumno,
                                                                   String apellidoAlumno,
                                                                   String tipoDocumentoAlumno,
                                                                   String numeroDocumentoAlumno,
                                                                   Long catedraId,
                                                                   Long trabajoPracticoId,
                                                                   Calendar fechaAsignacion) {

        List<AsignacionPaciente> entities = dao.findAsignacionesConfirmadas(
                nombreAlumno,
                apellidoAlumno,
                tipoDocumentoAlumno,
                numeroDocumentoAlumno,
                catedraId,
                trabajoPracticoId,
                fechaAsignacion);
        return asignacionPacienteMapper.toDTOList(entities);
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

        if (dto.getId() == null) {
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

        } else {
            asignacion = findById(dto.getId());
            asignacionPacienteMapper.updateFromDTO(dto, asignacion);
        }
        save(asignacion);
        return asignacionPacienteMapper.toDTO(asignacion);
    }

    public void cambiarEstadoAsignacion(UsuarioLogueadoDTO usuarioLogueadoDTO,
                                        AsignacionPacienteDTO dto, String estadoKey) throws SAPOException {
        AsignacionPaciente asignacion = dao.findById(dto.getId());
        Usuario usuario = usuarioService.findById(usuarioLogueadoDTO.getId());
        Paciente paciente = pacienteService.findById(dto.getIdPaciente());
        EstadoAsignacionPaciente estado = EstadoAsignacionPaciente.valueOf(estadoKey);
        if (estado == null) {
            return;
        }
        boolean sendCanceladoEmail = false;
        if (estado.equals(EstadoAsignacionPaciente.CANCELADO) && asignacion.getUltimoMovimiento().getEstado() == EstadoAsignacionPaciente.CONFIRMADO) {
            sendCanceladoEmail = true;
        }

        MovimientoAsignacionPaciente nuevoMovimientoAsignacion = new MovimientoAsignacionPaciente
                (estado, Calendar.getInstance(), usuario);
        asignacion.addMovimientoAsignacionPaciente(nuevoMovimientoAsignacion);
        asignacion.setUltimoMovimiento(nuevoMovimientoAsignacion);
        asignacion.setPaciente(paciente);

        if (estado.equals(EstadoAsignacionPaciente.CANCELADO) || estado.equals(EstadoAsignacionPaciente.ANULADO)) {
            MovimientoDiagnostico nuevoMovimientoDiagnostico = new MovimientoDiagnostico
                    (EstadoDiagnostico.PENDIENTE, Calendar.getInstance(), usuario);
            movimientoDiagnosticoService.save(nuevoMovimientoDiagnostico);
            asignacion.getDiagnostico().addMovimiento(nuevoMovimientoDiagnostico);
        }

        if (estado == EstadoAsignacionPaciente.CONFIRMADO) {
            mailService.sendAsignacionConfirmadaMail(
                    asignacion.getPaciente().getEmail(),
                    asignacion.getPaciente().getApellido(),
                    asignacion.getPaciente().getNombre(),
                    asignacion.getAlumno().getApellido(),
                    asignacion.getAlumno().getNombre(),
                    asignacion.getAlumno().getUsuario().getEmail(),
                    asignacion.getFechaAsignacion(),
                    asignacion.getCatedra().toString(),
                    asignacion.getTrabajoPractico().getPracticaOdontologica().getDenominacion()
            );
        }
        if (estado.equals(EstadoAsignacionPaciente.AUTORIZADO)) {
            Profesor profesor = (Profesor) usuarioService.findPersonaByUserAndRol(usuarioLogueadoDTO.getId(), RolEnum.PROFESOR);
            asignacion.setAutorizadoPor(profesor);
            mailService.sendAsignacionAutorizadaMail(
                    asignacion.getAlumno().getUsuario().getEmail(),
                    asignacion.getPaciente().getApellido(),
                    asignacion.getPaciente().getNombre(),
                    asignacion.getAlumno().getNombre(),
                    asignacion.getFechaAsignacion(),
                    asignacion.getCatedra().toString(),
                    asignacion.getTrabajoPractico().getPracticaOdontologica().getDenominacion(),
                    asignacion.getTrabajoPractico().getNombre(),
                    asignacion.getAutorizadoPor().getApellido() + " " + asignacion.getAutorizadoPor().getNombre());
        }

        if (sendCanceladoEmail) {
            mailService.sendAsignacionConfirmadaCanceladaMail(
                    asignacion.getPaciente().getEmail(),
                    asignacion.getPaciente().getApellido(),
                    asignacion.getPaciente().getNombre(),
                    asignacion.getAlumno().getApellido(),
                    asignacion.getAlumno().getNombre(),
                    asignacion.getAlumno().getUsuario().getEmail(),
                    asignacion.getFechaAsignacion(),
                    asignacion.getCatedra().toString(),
                    asignacion.getTrabajoPractico().getPracticaOdontologica().getDenominacion()
            );
        }
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

    public AsignacionPacienteDTO findDTOById(Long id) {
        AsignacionPaciente asignacionPaciente = this.findById(id);
        return asignacionPacienteMapper.toDTO(asignacionPaciente);
    }

    public Pair<String, byte[]> printAsignacionesPorAutorizar(List<Long> asignacionesIds) throws IOException {
        ArrayList<AsignacionPaciente> asignacionPacientes = new ArrayList<AsignacionPaciente>();
        for (Long id : asignacionesIds) {
            asignacionPacientes.add(findById(id));
        }
        File file = pdfGenerator.createAsignacionesListPdf(asignacionPacientes);
        return Pair.of(file.getName(), fileUtils.fileToArrayByte(file));
    }
}