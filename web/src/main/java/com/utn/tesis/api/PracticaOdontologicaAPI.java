package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.PracticaOdontologica;
import com.utn.tesis.service.PracticaOdontologicaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 15/02/15
 * Time: 17:07
 */

@Path("/practicaOdontologica")
@RequestScoped
public class PracticaOdontologicaAPI extends BaseAPI {

    @Inject
    private PracticaOdontologicaService practicaOdontologicaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(PracticaOdontologica practica) {
        try {
            if (practica.isNew()) {
                practica = practicaOdontologicaService.create(practica);
            } else {
                practicaOdontologicaService.update(practica);
            }

        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(practica).build();
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PracticaOdontologica> findByFilters(@QueryParam("denominacion") String denominacion,
                                       @QueryParam("idGrupoPractica") Long idGrupoPractica,
                                       @QueryParam("dadosBaja") boolean dadosBaja,
                                       @QueryParam("pageNumber") Long pageNumber,
                                       @QueryParam("pageSize") Long pageSize) {
        return practicaOdontologicaService.findByFilters(denominacion, idGrupoPractica, dadosBaja, pageNumber, pageSize);
    }

    @Path("/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(PracticaOdontologica practica) {
        try {
            practica = practicaOdontologicaService.remove(practica.getId(), practica.getMotivoBaja());
        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(practica).build();
    }

    @Path("/restore")
    @PUT
    public void restore(@QueryParam("id") Long id) {
        practicaOdontologicaService.restore(id);
    }

    @Path("/findById")
    @GET
    public PracticaOdontologica findById(@QueryParam("id") Long id) {
        return practicaOdontologicaService.findById(id);
    }
}
