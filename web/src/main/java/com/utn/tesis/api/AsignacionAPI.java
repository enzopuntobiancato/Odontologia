package com.utn.tesis.api;

import com.utn.tesis.SessionHelper;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.*;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;
import com.utn.tesis.util.DateParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/asignacion")
@Slf4j
@RequestScoped
public class AsignacionAPI extends BaseAPI {

    @Inject
    AsignacionPacienteService asignacionPacienteService;
    @Inject
    AlumnoService alumnoService;
    @Inject
    CatedraService catedraService;
    @Inject
    TrabajoPracticoService trabajoPracticoService;
    @Inject
    UsuarioService usuarioService;
    @Inject
    PersonaMapper personaMapper;
    @Inject
    TrabajoPracticoMapper trabajoPracticoMapper;
    @Inject
    CatedraMapper catedraMapper;
    @Inject
    AsignacionPacienteMapper asignacionPacienteMapper;
    @Inject
    MovimientoAsignacionMapper movimientoAsignacionMapper;
    @Inject
    EnumMapper enumMapper;
    @Inject
    private SessionHelper sessionHelper;
    @Inject
    private DiagnosticoService diagnosticoService;
    @Inject
    private UsuarioMapper usuarioMapper;

    @Path("/findAlumnoByFilters")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<AlumnoDTO> findAlumnoByFilters(@QueryParam("nombreAlumno") String nombreAlumno,
                                               @QueryParam("apellidoAlumno") String apellidoAlumno,
                                               @QueryParam("tipoDocumentoAlumno") String tipoDocumentoAlumno,
                                               @QueryParam("numeroDocumentoAlumno") String numeroDocumentoAlumno,
                                               @QueryParam("nombreUsuarioAlumno") String nombreUsuario,
                                               @QueryParam("sexoAlumno") String sexo,
                                               @QueryParam("pageNumber") Long pageNumber,
                                               @QueryParam("pageSize") Long pageSize) {
        try {
            Documento documento = null;
            if (tipoDocumentoAlumno != null && numeroDocumentoAlumno != null) {
                documento = new Documento(numeroDocumentoAlumno, TipoDocumento.valueOf(tipoDocumentoAlumno));
            }
            ArrayList<Alumno> alumnos = (ArrayList<Alumno>) alumnoService.findByFilters(nombreAlumno, apellidoAlumno,
                    documento, null, sexo != null ? Sexo.valueOf(sexo) : null, pageNumber, pageSize);
            return personaMapper.toAlumnoDTOList(alumnos);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ArrayList<AlumnoDTO>();
        }
    }

    //TODO: REVISAR LOS CAMPOS A MOSTRAR: CONTACTO O DESCRIPCION DEL DIAGNOSTICO?
    @Path("/findDiagnosticosByFilters")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<DiagnosticoSupport> findDiagnosticosByFilters(@QueryParam("tipoDocumentoPaciente") String tipoDocumentoPaciente,
                                                              @QueryParam("numeroDocumentoPaciente") String numeroDocumentoPaciente,
                                                              @QueryParam("nombrePaciente") String nombrePaciente,
                                                              @QueryParam("apellidoPaciente") String apellidoPaciente,
                                                              @QueryParam("catedraId") Long catedraId,
                                                              @QueryParam("trabajoPracticoId") Long trabajoPracticoId,
                                                              @QueryParam("pageNumber") Long pageNumber,
                                                              @QueryParam("pageSize") Long pageSize) {
        try {
            Documento documentoPaciente = null;
            if (tipoDocumentoPaciente != null && numeroDocumentoPaciente != null) {
                documentoPaciente = new Documento(numeroDocumentoPaciente, TipoDocumento.valueOf(tipoDocumentoPaciente));
            }
            List<DiagnosticoSupport> entities = asignacionPacienteService
                    .getDiagnosticosSupport(documentoPaciente, catedraId, trabajoPracticoId, null, pageNumber, pageSize);
            return entities;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return new ArrayList<DiagnosticoSupport>();
        }
    }

    //TODO: DEFINIR BIEN LOS CRITERIOS DE BUSQUEDA!
    @Path("/findAsignacionByFilters")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<AsignacionPacienteDTO> findAsignacionByFilters(@QueryParam("alumnoId") Long alumnoId,
                                                               @QueryParam("nombreAlumno") String nombreAlumno,
                                                               @QueryParam("apellidoAlumno") String apellidoAlumno,
                                                               @QueryParam("tipoDocumentoAlumno") String tipoDocumentoAlumno,
                                                               @QueryParam("numeroDocumentoAlumno") String numeroDocumentoAlumno,
                                                               @QueryParam("nombrePaciente") String nombrePaciente,
                                                               @QueryParam("apellidoPaciente") String apellidoPaciente,
                                                               @QueryParam("tipoDocumentoPaciente") String tipoDocumentoPaciente,
                                                               @QueryParam("numeroDocumentoPaciente") String numeroDocumentoPaciente,
                                                               @QueryParam("profesorId") Long profesorId,
                                                               @QueryParam("catedrasId") List<Long> catedrasId,
                                                               @QueryParam("trabajoPracticoId") Long trabajoPracticoId,
                                                               @QueryParam("estado") String estado,
                                                               @QueryParam("diagnosticoId") Long diagnosticoId,
                                                               @QueryParam("fechaAsignacion") DateParameter fechaAsignacion,
                                                               @QueryParam("dadosBaja") boolean dadosBaja,
                                                               @QueryParam("pageNumber") Long pageNumber,
                                                               @QueryParam("pageSize") Long pageSize) {
        try {
            Documento documentoPaciente = null;
            if (tipoDocumentoPaciente != null && numeroDocumentoPaciente != null) {
                documentoPaciente = new Documento(numeroDocumentoPaciente, TipoDocumento.valueOf(tipoDocumentoPaciente));
            }

            Documento documentoAlumno = null;
            if (tipoDocumentoAlumno != null && numeroDocumentoAlumno != null) {
                documentoAlumno = new Documento(numeroDocumentoAlumno, TipoDocumento.valueOf(tipoDocumentoAlumno));
            }
            EstadoAsignacionPaciente estadoAsignacionPaciente = (estado != null && StringUtils.isNotBlank(estado)
                    && EstadoAsignacionPaciente.valueOf(estado) != null)
                    ? EstadoAsignacionPaciente.valueOf(estado) : null;
            Calendar fecha = fechaAsignacion != null ? fechaAsignacion.getDate() : null;

            List<AsignacionPaciente> entities = asignacionPacienteService.findByFilters(alumnoId, nombreAlumno,
                    apellidoAlumno, documentoAlumno, profesorId, nombrePaciente, apellidoPaciente, documentoPaciente,
                    catedrasId, estadoAsignacionPaciente, diagnosticoId, null, fecha, trabajoPracticoId, dadosBaja, pageNumber, pageSize);

            entities = asignacionPacienteService.reloadList(entities, 4);
            return asignacionPacienteMapper.toDTOList(entities);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Path("/findAsignacionesConfirmadasAutorizadas")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<AsignacionPacienteDTO> findAsignacionesConfirmadasAutorizadas(@QueryParam("alumnoId") Long alumnoId,
                                                                              @QueryParam("nombreAlumno") String nombreAlumno,
                                                                              @QueryParam("apellidoAlumno") String apellidoAlumno,
                                                                              @QueryParam("tipoDocumentoAlumno") String tipoDocumentoAlumno,
                                                                              @QueryParam("numeroDocumentoAlumno") String numeroDocumentoAlumno,
                                                                              @QueryParam("nombrePaciente") String nombrePaciente,
                                                                              @QueryParam("apellidoPaciente") String apellidoPaciente,
                                                                              @QueryParam("tipoDocumentoPaciente") String tipoDocumentoPaciente,
                                                                              @QueryParam("numeroDocumentoPaciente") String numeroDocumentoPaciente,
                                                                              @QueryParam("profesorId") Long profesorId,
                                                                              @QueryParam("catedraId") Long catedraId,
                                                                              @QueryParam("trabajoPracticoId") Long trabajoPracticoId,
                                                                              @QueryParam("pageNumber") Long pageNumber,
                                                                              @QueryParam("pageSize") Long pageSize) {
        try {
            Documento documentoPaciente = null;
            if (tipoDocumentoPaciente != null && numeroDocumentoPaciente != null) {
                documentoPaciente = new Documento(numeroDocumentoPaciente, TipoDocumento.valueOf(tipoDocumentoPaciente));
            }

            Documento documentoAlumno = null;
            if (tipoDocumentoAlumno != null && numeroDocumentoAlumno != null) {
                documentoAlumno = new Documento(numeroDocumentoAlumno, TipoDocumento.valueOf(tipoDocumentoAlumno));
            }

            List<AsignacionPaciente> entities = asignacionPacienteService.findAsignacionesConfirmadasAutorizadas(alumnoId,
                    nombreAlumno, apellidoAlumno, documentoAlumno, profesorId, nombrePaciente, apellidoPaciente,
                    documentoPaciente, catedraId, trabajoPracticoId, pageNumber, pageSize);

            entities = asignacionPacienteService.reloadList(entities, 2);
            return asignacionPacienteMapper.toDTOList(entities);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(@Context HttpServletRequest request, AsignacionPacienteDTO dto) {
        try {
            UsuarioLogueadoDTO usuarioLogueadoDTO = sessionHelper.getUser(request);
            asignacionPacienteService.save(usuarioLogueadoDTO, dto);
            return Response.ok(dto).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/findById")
    @GET
    public AsignacionPacienteDTO findById(@QueryParam("id") Long id) {
        AsignacionPaciente entity = asignacionPacienteService.findById(id);
        entity = asignacionPacienteService.reload(entity, 2);
        return asignacionPacienteMapper.toDTO(entity);
    }

    @Path("/cambiarEstadoAsignacion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstadoAsignacion(@Context HttpServletRequest request,
                                            @QueryParam("estadoKey") String estadoKey,
                                            AsignacionPacienteDTO dto) throws SAPOException {
        try {
            UsuarioLogueadoDTO usuarioLogueadoDTO = sessionHelper.getUser(request);
            asignacionPacienteService.cambiarEstadoAsignacion(usuarioLogueadoDTO, dto, estadoKey);
            return Response.ok(true).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/autorizarAsignaciones")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autorizarAsignaciones(@Context HttpServletRequest request, List<AsignacionPacienteDTO> dtos) throws SAPOException {
        try {
            UsuarioLogueadoDTO usuarioLogueadoDTO = sessionHelper.getUser(request);
            for (AsignacionPacienteDTO dto : dtos) {
                asignacionPacienteService.cambiarEstadoAsignacion(usuarioLogueadoDTO, dto, "AUTORIZADO");
            }
            return Response.ok(true).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/getTrabajosPracticosByCatedra")
    @GET
    public List<TrabajoPracticoDTO> getTrabajosPracticosByCatedra(@QueryParam("idCatedra") Long idCatedra) {
        List<TrabajoPracticoDTO> trabajosPracticos = new ArrayList<TrabajoPracticoDTO>();
        try {
            if (idCatedra == null) {
                return trabajosPracticos;
            }
            return trabajoPracticoMapper.toDTOList(trabajoPracticoService.findByCatedra(idCatedra));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return trabajosPracticos;
        }
    }

    @Path("/estaAutorizado")
    @GET
    public boolean estaAutorizado(@QueryParam("trabajoPractico") Long trabajoPracticoId,
                                  @QueryParam("alumno") Long alumnoId) {
        if (trabajoPracticoId == null || alumnoId == null) {
            return false;
        }
        return asignacionPacienteService.estaAutorizado(alumnoId, trabajoPracticoId);
    }

    @Path("/findAlumnoByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AlumnoDTO findAlumnoByUser(@QueryParam("id") Long id) {
        return (AlumnoDTO)usuarioService.findPersonaDTOByUserAndRol(id, RolEnum.ALUMNO);
    }

    @Path("/findProfesorByUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProfesorDTO findProfesorByUser(@QueryParam("id") Long id) {
        return  (ProfesorDTO)usuarioService.findPersonaDTOByUserAndRol(id, RolEnum.PROFESOR);
    }

    @Path("/getEstadosAsignaciones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> getEstadosAsignaciones() {
        return asignacionPacienteService.getEstadosAsignaciones();
    }

    @Path("/getCatedras")
    @GET
    public List<CatedraConsultaDTO> getCatedras() {
        return catedraMapper.toConsultaDTOList(catedraService.findAll());
    }

    @Path("/findCatedrasByProfesor")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatedraConsultaDTO> findCatedrasByProfesor(@QueryParam("profesorId") Long profesorId) {
        List<Catedra> entities = catedraService.findCatedrasByProfesor(profesorId);
        entities = catedraService.reloadList(entities, 1);
        return catedraMapper.toConsultaDTOList(entities);
    }

    @Path("/getTrabajosPracticos")
    @GET
    public List<TrabajoPracticoDTO> getTrabajosPracticos() {
        return trabajoPracticoMapper.toDTOList(trabajoPracticoService.findAll());
    }
}
