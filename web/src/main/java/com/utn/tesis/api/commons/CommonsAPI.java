package com.utn.tesis.api.commons;

import com.utn.tesis.annotation.JsonMap;
import com.utn.tesis.model.Dia;
import com.utn.tesis.model.GrupoPracticaOdontologica;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.model.Rol;
import com.utn.tesis.service.CommonsService;
import com.utn.tesis.service.initialization.InitializationService;
import com.utn.tesis.util.MappingUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commons")
@RequestScoped
public class CommonsAPI {

    @Inject
    CommonsService commonsService;

    @Inject
    InitializationService initService;

    @Path("/getNiveles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Nivel> findAllNiveles() {
        return commonsService.findAllNiveles();
    }

    @Path("/getDias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dia> findAllDias() {
        return commonsService.findAllDias();
    }

    @Path("/getGruposPracticaOdontologica")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GrupoPracticaOdontologica> findAllGruposPracticaOdontologica() {
        return commonsService.findAllGruposPracticaOdontologica();
    }

    @Path("/initializeData")
    @POST
    public Response loadInitializationData() {
        try {
            boolean result = initService.initializeData();
            return Response.status(Response.Status.OK).entity(result).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/getRoles")
    @GET
    public List<Rol> findAllRoles() {
        return (List<Rol>)MappingUtil.serializeWithView(commonsService.findAllRoles(), JsonMap.Public.class);
    }
}
