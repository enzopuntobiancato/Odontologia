package com.utn.tesis.api;

import com.utn.tesis.SessionHelper;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.DiagnosticoDTO;
import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.DiagnosticoMapper;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.EstadoDiagnostico;
import com.utn.tesis.service.DiagnosticoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Path("/diagnostico")
@RequestScoped
@Slf4j
public class DiagnosticoAPI extends BaseAPI {
    @Inject
    private DiagnosticoService diagnosticoService;
    @Inject
    private DiagnosticoMapper diagnosticoMapper;
    @Inject
    private EnumMapper enumMapper;
    @Inject
    private SessionHelper sessionHelper;

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DiagnosticoDTO> findByFilters(@QueryParam("practicaId") Long practicaId,
                                              @QueryParam("estado") EstadoDiagnostico estado,
                                              @QueryParam("fechaDesde") String fechaDesdeStr,
                                              @QueryParam("fechaHasta") String fechaHastaStr,
                                              @QueryParam("pageNumber") Long pageNumber,
                                              @QueryParam("pageSize") Long pageSize,
                                              @PathParam("id") Long pacienteId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date fechaDesde = null;
        Date fechaHasta = null;
        if (StringUtils.isNotBlank(fechaDesdeStr)) {
            fechaDesde = sdf.parse(fechaDesdeStr);
        }
        if(StringUtils.isNotBlank(fechaHastaStr)) {
            fechaHasta = sdf.parse(fechaHastaStr);
        }
        return diagnosticoService.findByFilters(practicaId, estado, fechaDesde, fechaHasta, pacienteId, pageNumber, pageSize);
    }

    @GET
    @Path("/findAllDiagnosticosByPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DiagnosticoDTO> findDiagnosticosByPaciente(@QueryParam("pacienteId") Long pacienteId) {
        List<Diagnostico> diagnosticos = diagnosticoService.findAllDiagnosticosByPaciente(pacienteId);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @GET
    @Path("/findOpenDiagnosticosByPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DiagnosticoDTO> findOpenDiagnosticosByPaciente(@QueryParam("pacienteId") Long pacienteId) {
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.findOpenDiagnosticosByPaciente(pacienteId);
        return diagnosticos;
    }

    @GET
    @Path("/findFinalStates")
    public List<EnumDTO> findFinalStates() {
        return enumMapper.estadoDiagnosticoListToDTOList(EstadoDiagnostico.MANUAL_POSSIBLE_TRANSITIONS);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save/{id}")
    public Response save(@Context HttpServletRequest request, List<DiagnosticoDTO> diagnosticos, @PathParam("id") Long pacienteId) throws SAPOException {
        UsuarioLogueadoDTO usuario = sessionHelper.getUser(request);
        diagnosticoService.saveDiagnosticosByPaciente(diagnosticos, pacienteId, usuario);
        return Response.ok().build();
    }
}
