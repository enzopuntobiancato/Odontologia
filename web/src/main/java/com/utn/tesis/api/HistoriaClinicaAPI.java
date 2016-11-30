package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.api.commons.MultiPartFormHelper;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.ArchivoDTO;
import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.HistoriaClinicaMapper;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.service.ArchivoService;
import com.utn.tesis.service.HistoriaClinicaService;
import com.utn.tesis.service.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    @Inject
    private MultiPartFormHelper helper;
    @Inject
    private ArchivoService archivoService;

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

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveNewFiles")
    public Response saveDocumentacionesFiles(MultipartFormDataInput input) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();
        Long[] newArchivosIds = (Long[]) helper.retrieveObject(form, "newFilesIds", Long[].class);
        List<Map<String, Object>> files = helper.retrieveFiles(form, "files");
        List<ArchivoDTO> archivoDTOs = new ArrayList<ArchivoDTO>();
        for (Long newArchivo: newArchivosIds) {
            ArchivoDTO archivoDTO = archivoService.findArchivo(newArchivo);
            Map<String, Object> file = helper.findFile(files, archivoDTO.getNombre());
            archivoDTO.setExtension((FileExtension) file.get(helper.EXTENSION));
            archivoDTO.setArchivo((InputStream) file.get(helper.FILE));
            archivoDTOs.add(archivoDTO);
        }
        archivoService.saveList(archivoDTOs);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveDocumentaciones/{idPaciente}")
    public Response saveDocumentacionesByPaciente(List<ArchivoDTO> documentaciones, @PathParam("idPaciente") Long idPaciente) throws SAPOException {
        List<Long> newFiles = pacienteService.updateCurrentDocsDescriptionAndSaveTempNewDocs(documentaciones, idPaciente);
        return Response.ok(newFiles).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findDocumentaciones")
    public List<ArchivoDTO> findDocumentacionesByPaciente(@QueryParam("pacienteId") Long pacienteId) {
        return pacienteService.findDocumentacionesByPaciente(pacienteId);
    }

    @GET
    @Path("/printHC")
    @Produces({"application/pdf"})
    public Response getPdf(@QueryParam("idPaciente") Long idPaciente) {
        Response.ResponseBuilder response = Response.noContent();
        if (idPaciente != null) {
            try {
                Pair<String, byte[]> pdf = pacienteService.generateHCPDF(idPaciente);
                response = Response.ok(pdf.getRight());
                response.header("Content-Disposition", String.format("attachment; filename=\"%s\"", pdf.getLeft()));
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return response.build();
    }
}
