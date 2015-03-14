package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.model.Materia;
import com.utn.tesis.model.Nivel;
import com.utn.tesis.service.MateriaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/materia")
@RequestScoped
public class MateriaAPI extends BaseAPI {

    @Inject private MateriaService materiaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create(Materia materia) {
        try {
            materia = materiaService.create(materia);
        } catch (Exception e)
        {
            return persistenceRequest(e);
        }

        return Response.ok(materia).build();
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> findByFilters(@QueryParam("nombre") String nombre, @QueryParam("nivel") String nivel) {
        return materiaService.findByFilters(nombre, nivel != null ? Nivel.valueOf(nivel) : null);
    }

}
