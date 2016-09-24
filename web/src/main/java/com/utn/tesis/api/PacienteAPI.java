package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.HistoriaClinicaMapper;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.HistoriaClinicaService;
import com.utn.tesis.service.PacienteService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 16/02/16
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
@Path("/paciente")
@Slf4j
@RequestScoped
public class PacienteAPI extends BaseAPI {
    @Inject
    private HistoriaClinicaService historiaClinicaService;
    @Inject
    PacienteService pacienteService;
    @Inject
    CommonsService commonsService;
    @Inject
    PacienteMapper pacienteMapper;
    @Inject
    private HistoriaClinicaMapper historiaClinicaMapper;

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(PacienteDTO pacienteDTO) throws SAPOException {
        Paciente entity = pacienteMapper.fromDTO(pacienteDTO);
        if (entity.isNew()) {
            entity.setFechaCarga(Calendar.getInstance());
        } else {
            pacienteMapper.updataFromDTO(pacienteDTO, entity);
        }
        pacienteDTO = pacienteService.savePaciente(entity);
        return Response.ok(pacienteDTO).build();
    }

    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<PacienteDTO> findByFilters(@QueryParam("nombre") String nombre,
                                           @QueryParam("apellido") String apellido,
                                           @QueryParam("tipoDocumento") String tipoDocumento,
                                           @QueryParam("numeroDocumento") String numeroDocumento,
                                           @QueryParam("sexo") String sexo,
                                           @QueryParam("dadosBaja") boolean dadosBaja,
                                           @QueryParam("pageNumber") Long pageNumber,
                                           @QueryParam("pageSize") Long pageSize) {
        Documento documento = new Documento(numeroDocumento, tipoDocumento !=  null ? TipoDocumento.valueOf(tipoDocumento): null);
        ArrayList<Paciente> pacientes = (ArrayList<Paciente>) pacienteService.findByFilters(nombre, apellido,
                documento, sexo != null ? Sexo.valueOf(sexo) : null, dadosBaja, pageNumber, pageSize);

        ArrayList<PacienteDTO> pacienteDTOs = (ArrayList<PacienteDTO>) pacienteMapper.toDTOList(pacientes);
        return pacienteDTOs;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO findById(@QueryParam("id") Long id) {
        Paciente paciente = pacienteService.findById(id);
        HistoriaClinica historiaClinica;
        if (paciente == null) {
            return null;
        }

        historiaClinica = historiaClinicaService.findById(paciente.getHistoriaClinica().getId());
        PacienteDTO pacienteDTO = pacienteMapper.toDTO(paciente);
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDTO(historiaClinica);
        pacienteDTO.setHistoriaClinicaDTO(historiaClinicaDTO);

        return pacienteDTO;
    }

    @Path("/findPacienteLightById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO findPacienteLightById(@QueryParam("id") Long id) {
        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            return null;
        }
        PacienteDTO pacienteDTO = pacienteMapper.toDTO(paciente);

        return pacienteDTO;
    }

    @Path("/initPaciente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO initPaciente() {

        Paciente paciente = new Paciente();
        HistoriaClinica historiaClinica = HistoriaClinica.createDefault();
        paciente.setHistoriaClinica(historiaClinica);

        PacienteDTO pacienteDTO = pacienteMapper.toDTO(paciente);
        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDTO(historiaClinica);
        pacienteDTO.setHistoriaClinicaDTO(historiaClinicaDTO);

        return pacienteDTO;
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(PacienteDTO dto) throws SAPOException {
        Paciente entity = pacienteService.remove(dto.getId(), dto.getMotivoBaja());
        dto = pacienteMapper.toDTO(entity);
        return Response.ok(dto).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        pacienteService.restore(id);
    }
}
