package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.mapping.mapper.*;
import com.utn.tesis.model.*;
import com.utn.tesis.service.*;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    AlumnoService alumnoService;
    @Inject
    CatedraService catedraService;
    @Inject
    TrabajoPracticoService trabajoPracticoService;
    @Inject
    PacienteMapper pacienteMapper;
    @Inject
    PersonaMapper personaMapper;
    @Inject
    TrabajoPracticoMapper trabajoPracticoMapper;
    @Inject
    CatedraMapper catedraMapper;

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
                documento,sexo != null ? Sexo.valueOf(sexo) : null, false, pageNumber,pageSize);

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


    @Path("/findAllCatedras")
    @GET
    public List<CatedraConsultaDTO> findAllCatedras() {
        return catedraMapper.toConsultaDTOList(catedraService.findAll());
    }

    @Path("/findAllTrabajosPracticos")
    @GET
    public List<TrabajoPracticoDTO> findAllTrabajosPracticos() {
        return trabajoPracticoMapper.toDTOList(trabajoPracticoService.findAll());
    }
}
