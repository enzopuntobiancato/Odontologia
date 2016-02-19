package com.utn.tesis.api.commons;

import com.utn.tesis.mapping.dto.*;
import com.utn.tesis.model.*;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.initialization.InitializationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commons")
@RequestScoped
public class CommonsAPI {

    @Inject
    private CommonsService commonsService;

    @Inject
    private InitializationService initService;

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
            boolean result = initService.initializeData();
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
    public List<EnumDTO> getEstadosCivil(){
        return commonsService.findEstadosCivil();
    }


    @Path("/getProvincias")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<ProvinciaDTO> getProvincias(){
        return commonsService.findAllProvincias();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getCiudades")
    public List<CiudadDTO> getCiudades(){
        return commonsService.findAllCiudades();
    }

    @GET
    @Path("/getBarrios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BarrioDTO> getBarrios(){
        return commonsService.findAllBarrios();
    }

    @GET
    @Path("/{name}")
    @Produces("image/jpeg")
    public byte[] get(@PathParam("name") String name) {

//        Monitor m = Monitor.getMonitor(name);
//
//        if (m == null) {
//            return null;
//        }
//
//        ByteArrayOutputStream bo = new ByteArrayOutputStream(2048);
//        try {
//            ImageIO.write(m.getImage(),"jpeg",bo);
//        } catch (IOException ex) {
//            return null;
//        }
//
//        return bo.toByteArray();
        return null;
    }
}
