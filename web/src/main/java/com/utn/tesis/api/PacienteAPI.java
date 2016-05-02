package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.service.CommonsService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class PacienteAPI extends BaseAPI{
    @Inject
    PacienteService pacienteService;
    @Inject
    PacienteMapper pacienteMapper;
    @Inject
    CommonsService commonsService;

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(PacienteDTO pacienteDTO){
        Paciente entity = pacienteMapper.fromDTO(pacienteDTO);
        try {
            if (entity.isNew()){
                entity.setFechaCarga(Calendar.getInstance());
            }
            else {
                  pacienteMapper.updataFromDTO(pacienteDTO,entity);
            }
            pacienteService.save(entity);
            return Response.ok(pacienteDTO).build();
        } catch (SAPOException se) {
            return persistenceRequest(se);
        } catch (Exception e) {
            Logger.getLogger(BaseAPI.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<PacienteDTO> findByFilters(@QueryParam("nombre")String nombre,
                                           @QueryParam("apellido") String apellido,
                                           @QueryParam("tipoDocumento") String tipoDocumento,
                                           @QueryParam("numeroDocumento") String numeroDocumento,
                                           @QueryParam("nombreUsuario") String nombreUsuario,
                                           @QueryParam("sexo") String sexo,
                                           @QueryParam("pageNumber") Long pageNumber,
                                           @QueryParam("pageSize") Long pageSize){
        Documento documento = null;
        if(tipoDocumento != null && numeroDocumento != null){
           documento = new Documento(numeroDocumento,TipoDocumento.valueOf(tipoDocumento));
        }
        ArrayList<Paciente> pacientes = (ArrayList<Paciente>) pacienteService.findByFilters(nombre,apellido,
                documento, nombreUsuario,sexo != null ? Sexo.valueOf(sexo) : null,pageNumber,pageSize);

        ArrayList<PacienteDTO> pacienteDTOs = (ArrayList<PacienteDTO>) pacienteMapper.toDTOList(pacientes);
        return pacienteDTOs;
    }

    @Path("/findById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO findById(@QueryParam("id") Long id){
        Paciente paciente = pacienteService.findById(id);
        PacienteDTO auxpaciente = pacienteMapper.toDTO(pacienteService.findById(id));
        return pacienteMapper.toDTO(pacienteService.findById(id));
    }

    //TODO: Persona no extiende de Bajeable, por lo tanto no puede ser dado de baja. Se debe revisar cómo implementar la herencia para poder darlo de baja.
    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(PacienteDTO dto) {
        try {
            Paciente entity = pacienteService.remove(dto.getId(), "Baja");
            dto = pacienteMapper.toDTO(entity);
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(dto).build();
    }

    //TODO: Se debe revisar este método o quitarlo si hace falta.
    @Path("/restore")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void restore(@QueryParam("id") Long id){
            pacienteService.restore(id);
    }
}
