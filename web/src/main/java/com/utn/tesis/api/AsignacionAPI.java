package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.mapping.dto.AlumnoDTO;
import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.mapping.dto.PacienteDTO;
import com.utn.tesis.mapping.mapper.HistoriaClinicaMapper;
import com.utn.tesis.mapping.mapper.PacienteMapper;
import com.utn.tesis.mapping.mapper.PersonaMapper;
import com.utn.tesis.model.*;
import com.utn.tesis.service.AlumnoService;
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
@Path("/asignacion")
@Slf4j
@RequestScoped
public class AsignacionAPI extends BaseAPI{

    @Inject
    PacienteService pacienteService;
    @Inject
    PacienteMapper pacienteMapper;
    @Inject
    AlumnoService alumnoService;
    @Inject
    PersonaMapper personaMapper;

    @Path("/findPacientes")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<PacienteDTO> findPacienteByFilters(@QueryParam("nombre")String nombre,
                                           @QueryParam("apellido") String apellido,
                                           @QueryParam("tipoDocumento") String tipoDocumento,
                                           @QueryParam("numeroDocumento") String numeroDocumento,
                                           @QueryParam("sexo") String sexo,
                                           @QueryParam("pageNumber") Long pageNumber,
                                           @QueryParam("pageSize") Long pageSize){
        Documento documento = null;
        if(tipoDocumento != null && numeroDocumento != null){
           documento = new Documento(numeroDocumento,TipoDocumento.valueOf(tipoDocumento));
        }
        ArrayList<Paciente> pacientes = (ArrayList<Paciente>) pacienteService.findByFilters(nombre,apellido,
                documento, null,sexo != null ? Sexo.valueOf(sexo) : null,pageNumber,pageSize);

        ArrayList<PacienteDTO> pacienteDTOs = (ArrayList<PacienteDTO>) pacienteMapper.toDTOList(pacientes);
        return pacienteDTOs;
    }

    @Path("/findAlumnos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<AlumnoDTO> findAlumnoByFilters(@QueryParam("nombre")String nombre,
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
        ArrayList<Alumno> alumnos = (ArrayList<Alumno>) alumnoService.findByFilters(nombre,apellido,
                documento, null,sexo != null ? Sexo.valueOf(sexo) : null,pageNumber,pageSize);
        List<AlumnoDTO> alumnoDTOs = personaMapper.toAlumnoDTOList(alumnos);

        return alumnoDTOs;
    }
}
