package com.utn.tesis.api;

import com.utn.tesis.SessionHelper;
import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.api.commons.MultiPartFormHelper;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.DiagnosticoDTO;
import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.mapping.dto.MovimientoDiagnosticoDTO;
import com.utn.tesis.mapping.dto.UsuarioLogueadoDTO;
import com.utn.tesis.mapping.mapper.DiagnosticoMapper;
import com.utn.tesis.mapping.mapper.EnumMapper;
import com.utn.tesis.model.Diagnostico;
import com.utn.tesis.model.EstadoDiagnostico;
import com.utn.tesis.model.MovimientoDiagnostico;
import com.utn.tesis.model.odontograma.*;
import com.utn.tesis.service.DiagnosticoService;
import com.utn.tesis.service.HistoriaClinicaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Inject
    private MultiPartFormHelper helper;
    @Inject
    private HistoriaClinicaService historiaClinicaService;

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
        if (StringUtils.isNotBlank(fechaHastaStr)) {
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save/{id}")
    public List<DiagnosticoDTO> save(@Context HttpServletRequest request, MultipartFormDataInput input, @PathParam("id") Long pacienteId) throws SAPOException {
        UsuarioLogueadoDTO usuario = sessionHelper.getUser(request);

        Map<String, List<InputPart>> form = input.getFormDataMap();
        DiagnosticoDTO[] diagnosticosArray = (DiagnosticoDTO[]) helper.retrieveObject(form, "diagnosticos", DiagnosticoDTO[].class);
        PiezaDental[] piezasDentalesArray = (PiezaDental[]) helper.retrieveObject(form, "piezas", PiezaDental[].class);
        List<PiezaDental> piezas = Arrays.asList(piezasDentalesArray);
        //Guardamos los diagnosticos.
        List<DiagnosticoDTO> diagnosticoDTOs = diagnosticoMapper.toDTOList(diagnosticoService.saveDiagnosticosByPaciente
                (Arrays.asList(diagnosticosArray), pacienteId, usuario));
        //Recuperamos los diagnosticos guardados para seteale el ID a las piezas.
        for (DiagnosticoDTO d : diagnosticoDTOs) {
            if (d.getPiezas() == null || d.getPiezas().size() == 0) {
                continue;
            }

            List<Integer> numerosPiezas = d.getPiezas();
            for (Integer numero : numerosPiezas) {
                PiezaDental p = findPiezaInList(piezas, numero);
                if (p == null) {
                    continue;
                }
                p.setDiagnosticoId(d.getId());
            }
        }
        //Guardamos el odontograma con las piezas.
        historiaClinicaService.saveOdontogramaByPaciente(Arrays.asList(piezasDentalesArray), pacienteId);
        return findOpenDiagnosticosByPaciente(pacienteId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getHallazgos")
    public List<HallazgoClinico> getHallazgosClinicos() {
        List<HallazgoClinico> hallazgos = new ArrayList<HallazgoClinico>();
        hallazgos.add(new Caries());
        hallazgos.add(new Corona());
        hallazgos.add(new Extraccion());
        hallazgos.add(new ProtesisCompleta());
        hallazgos.add(new ProtesisParcial());
        hallazgos.add(new Puente());
        hallazgos.add(new Sellador());
        hallazgos.add(new TratamientoDeConducto());
        return hallazgos;
    }

    //AUXILIARES
    private PiezaDental findPiezaInList(List<PiezaDental> piezas, Integer nombrePieza) {
        for (PiezaDental p : piezas) {
            if (p.getNombrePiezaDental() != nombrePieza) {
                continue;
            }
            return p;
        }
        return null;
    }
}
