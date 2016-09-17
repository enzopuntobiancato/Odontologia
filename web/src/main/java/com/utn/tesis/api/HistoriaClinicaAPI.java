package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.HistoriaClinicaMapper;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.model.*;
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
import java.util.Date;
import java.util.List;

@Path("/historiaClinica")
@RequestScoped
@Slf4j
public class HistoriaClinicaAPI extends BaseAPI {
    @Inject
    private HistoriaClinicaService historiaClinicaService;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private PacienteMapper pacienteMapper;
    @Inject
    private HistoriaClinicaMapper historiaClinicaMapper;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HistoriaClinicaDTO> findByFilters(@QueryParam("numero") int numero,
                                                  @QueryParam("fechaApertura") Date fechaApertura,
                                                  @QueryParam("dadosBaja") boolean dadosBaja,
                                                  @QueryParam("pageNumber") Long pageNumber,
                                                  @QueryParam("pageSize") Long pageSize) {

        List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByFilters(null, null, null, null, null, null,
                pageNumber, pageSize);
        List<HistoriaClinicaDTO> historiasClinicaDTOs = historiaClinicaMapper.toDTOList(historiasClinicas);
        return historiasClinicaDTOs;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO findById(@QueryParam("id") Long id) {
        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            return null;
        }

        PacienteDTO pacienteDTO = pacienteMapper.toDTO(paciente);
        HistoriaClinica historiaClinica = historiaClinicaService.findById(pacienteDTO.getHistoriaClinicaId());
        if (historiaClinica == null) {
            return null;
        }

        HistoriaClinicaDTO historiaClinicaDTO = historiaClinicaMapper.toDTO(historiaClinica);
        pacienteDTO.setHistoriaClinicaDTO(historiaClinicaDTO);

        return pacienteDTO;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(HistoriaClinicaDTO dto) throws SAPOException {
        HistoriaClinica entity;
        if (dto.getId() == null) {
            entity = historiaClinicaMapper.fromDTO(dto);
            entity.setFechaApertura(Calendar.getInstance());
        } else {
            entity = historiaClinicaService.findById(dto.getId());
            historiaClinicaMapper.updateHistoriaClinicaFromDTO(dto, entity);
        }

        dto = historiaClinicaMapper.toDTO(historiaClinicaService.save(entity));
        return Response.ok(dto).build();
    }

    @Path("/findPacientes")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<PacienteDTO> findPacientesByFilters(@QueryParam("nombre") String nombre,
                                                    @QueryParam("apellido") String apellido,
                                                    @QueryParam("tipoDocumento") String tipoDocumento,
                                                    @QueryParam("numeroDocumento") String numeroDocumento,
                                                    @QueryParam("sexo") String sexo,
                                                    @QueryParam("pageNumber") Long pageNumber,
                                                    @QueryParam("pageSize") Long pageSize) {

        Documento documento = null;
        if (tipoDocumento != null && numeroDocumento != null) {
            documento = new Documento(numeroDocumento, TipoDocumento.valueOf(tipoDocumento));
        }
        ArrayList<Paciente> pacientes = (ArrayList<Paciente>) pacienteService.findByFilters(nombre, apellido,
                documento, sexo != null ? Sexo.valueOf(sexo) : null, false, pageNumber, pageSize);

        ArrayList<PacienteDTO> pacienteDTOs = (ArrayList<PacienteDTO>) pacienteMapper.toDTOList(pacientes);
        return pacienteDTOs;
    }
}
