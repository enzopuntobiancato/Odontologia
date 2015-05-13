package com.utn.tesis.api;

import com.utn.tesis.api.commons.BaseAPI;
import com.utn.tesis.exception.SAPOException;
import com.utn.tesis.model.TrabajoPractico;
import com.utn.tesis.service.TrabajoPracticoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:50
 */
@Path("/trabajoPractico")
@RequestScoped
public class TrabajoPracticoAPI extends BaseAPI {

    @Inject
    TrabajoPracticoService trabajoPracticoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response save(TrabajoPractico trabajoPractico) {
        try {
            if (trabajoPractico.isNew()) {
                trabajoPractico = trabajoPracticoService.create(trabajoPractico);
            } else {
                trabajoPracticoService.update(trabajoPractico);
            }

        } catch (SAPOException se) {
            return persistenceRequest(se);
        }
        return Response.ok(trabajoPractico).build();
    }

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrabajoPractico> findByFilters(@QueryParam("nombre") String nombre, @QueryParam("grupoPracticaId") Long grupoPracticaId,
                                               @QueryParam("practicaId") Long practicaId, @QueryParam("dadosBaja") boolean dadosBaja,
                                                @QueryParam("pageNumber") Long pageNumber, @QueryParam("pageSize") Long pageSize ) {
        return trabajoPracticoService.findByFilters(nombre, grupoPracticaId, practicaId, dadosBaja, pageNumber, pageSize);
    }



}
