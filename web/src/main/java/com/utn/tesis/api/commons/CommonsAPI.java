package com.utn.tesis.api.commons;

import com.google.common.collect.ImmutableMap;
import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.service.BarrioService;
import com.utn.tesis.service.CiudadService;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.ProvinciaService;
import com.utn.tesis.service.initialization.InitializationInvoker;
import com.utn.tesis.service.initialization.InitializationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/commons")
@RequestScoped
public class CommonsAPI {
    @Inject
    private CommonsService commonsService;
    @Inject
    private InitializationInvoker initializationInvoker;
    @Inject
    private ProvinciaService provinciaService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private BarrioService barrioService;

    @Path("/getNiveles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllNiveles() {
        return commonsService.findAllNiveles();
    }

    @Path("/getRoles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RolDTO> findAllRoles(){
        return commonsService.findAllRoles();
    }

    @Path("/getDias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllDias() {
        return commonsService.findAllDias();
    }

    @Path("/getGruposPracticaOdontologica")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GrupoPracticaOdontologicaDTO> findAllGruposPracticaOdontologica() {
        return commonsService.findAllGruposPracticaOdontologica();
    }

    @Path("/initializeData")
    @POST
    public Response loadInitializationData() {
        try {
            boolean result = initializationInvoker.initializeData();
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/getTiposDocumento")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllTiposDocumento() {
        return commonsService.findAllTiposDocumento();
    }

    @Path("/getSexos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllSexos() {
        return commonsService.findAllSexos();
    }

    @Path("/getCargos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllCargos() {
        return commonsService.findAllCargos();
    }

    @Path("/getEstadosCivil")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllEstadosCivil(){
        return commonsService.findAllEstadosCivil();
    }

    @Path("/getNivelesEstudio")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllNivelesEstudio(){
        return commonsService.findAllNivelesEstudio();
    }

    @Path("/getObrasSociales")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ObraSocialDTO> findAllObrasSociales(){
        return commonsService.findAllObrasSociales();
    }

    @Path("/getTrabajos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrabajoDTO> findAllTrabajos(){
        return commonsService.findAllTrabajos();
    }

    @Path("/getNacionalidades")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllNacionalidades(){
        return commonsService.findAllNacionalidades();
    }

    @Path("/getPersonaEnums")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<EnumDTO>> findPersonaEnums() {
        return ImmutableMap.of(
                "sexos", findAllSexos(),
                "cargos", findAllCargos(),
                "tiposDocumento", findAllTiposDocumento()
        );
    }

    @Path("/getEstadosDiagnostico")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnumDTO> findAllEstadosDiagnostico() {
        return commonsService.findAllEstadosDiagnostico();
    }
}
