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
import com.utn.tesis.model.odontograma.Odontograma;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Path("/paciente")
@Slf4j
@RequestScoped
public class PacienteAPI extends BaseAPI {
    @Inject
    private PacienteService pacienteService;
    @Inject
    private CommonsService commonsService;
    @Inject
    private PacienteMapper pacienteMapper;
    @Inject
    private HistoriaClinicaMapper historiaClinicaMapper;
    @Inject
    private MultiPartFormHelper helper;

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
        PacienteDTO pacienteDTO = pacienteService.findDTOById(id);

        return pacienteDTO;
    }

    @Path("/findPacienteLightById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO findPacienteLightById(@QueryParam("id") Long id) {
        PacienteDTO pacienteDTO = pacienteService.findLightDTOById(id);

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

    @Path("/initOdontograma")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Odontograma initOdontograma() {
        Odontograma odontograma = Odontograma.createDefault();
        return odontograma;
    }

    @Path("/findOdontogramaById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Odontograma findOdontogramaById(@QueryParam("pacienteId") Long pacienteId) {
        Paciente paciente = pacienteService.findById(pacienteId);
        return pacienteService.findOdontogramaById(paciente.getHistoriaClinica().getId());
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/savePacienteImage/{pacienteId}")
    public Response saveUserImage(MultipartFormDataInput input, @PathParam("pacienteId") Long pacienteId) throws SAPOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();

        Map<String, Object> file = helper.retrieveFile(form, "file");
        ArchivoDTO imagenPaciente = null;
        if (file != null) {
            imagenPaciente = new ArchivoDTO();
            imagenPaciente.setExtension((FileExtension) file.get(helper.EXTENSION));
            imagenPaciente.setNombre((String) file.get(helper.NAME));
            imagenPaciente.setArchivo((InputStream) file.get(helper.FILE));
        }
        PacienteDTO pacienteDTO = pacienteService.savePacienteImage(imagenPaciente, pacienteId);
        return Response.ok(pacienteDTO).build();
    }

    @Path("/findOdontogramaUriById")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String findOdontogramaUriById(@QueryParam("pacienteId") Long pacienteId) {
        Paciente paciente = pacienteService.findById(pacienteId);
        return pacienteService.findOdontogramaUriById(paciente.getHistoriaClinica().getId());
    }


}
